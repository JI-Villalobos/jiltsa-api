package com.jiltsa.admin.common.openapi;

import com.jiltsa.admin.common.web.PageRequestResolver;
import com.jiltsa.admin.security.AdminOnly;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.QueryParameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.validation.Valid;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.HandlerMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * OpenAPI description at /v3/api-docs, Swagger UI at /swagger-ui.html.
 * Everything except /auth/authenticate needs a bearer JWT; errors are RFC 7807 ProblemDetail.
 */
@Configuration
public class OpenApiConfig {
    static final String BEARER = "bearerAuth";
    static final String PROBLEM = "ProblemDetail";
    static final String PROBLEM_JSON = "application/problem+json";
    private static final String PUBLIC_LOGIN = "/jiltsa/api/v1/auth/authenticate";
    private static final String ROOT_PACKAGE = "com.jiltsa.admin.";

    @Bean
    OpenAPI jiltsaOpenApi(Optional<BuildProperties> build) {
        Map<String, Schema> problemSchemas = ModelConverters.getInstance().readAll(ProblemDetail.class);
        Components components = new Components()
                .addSecuritySchemes(BEARER, new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                        .description("Token from POST " + PUBLIC_LOGIN));
        problemSchemas.forEach(components::addSchemas);
        return new OpenAPI()
                .info(new Info()
                        .title("Jiltsa API")
                        .version(build.map(BuildProperties::getVersion).orElse("dev"))
                        .description("Cash proof, billing, orders and operative reports for the Jiltsa branches. "
                                + "Paginated endpoints take page, elements, sortBy and sortDirection (asc|desc)."))
                .components(components)
                .addSecurityItem(new SecurityRequirement().addList(BEARER));
    }

    /** Per-operation: module tag, paging parameters, security exception for login, error responses. */
    @Bean
    OperationCustomizer jiltsaOperationCustomizer() {
        return (Operation operation, HandlerMethod handler) -> {
            operation.setTags(List.of(moduleOf(handler)));
            documentPaging(operation, handler);

            ApiResponses responses = operation.getResponses();
            boolean isLogin = handler.getMethod().getName().equals("authenticate")
                    && handler.getBeanType().getSimpleName().equals("AuthenticationController");
            if (isLogin) {
                operation.setSecurity(List.of());
                responses.addApiResponse("401", problem("Wrong credentials"));
            } else {
                responses.addApiResponse("401", problem("Missing, invalid or expired token"));
            }
            if (handler.hasMethodAnnotation(AdminOnly.class) || handler.getBeanType().isAnnotationPresent(AdminOnly.class)) {
                responses.addApiResponse("403", problem("Requires the ADMIN role"));
            }
            if (hasValidatedBody(handler)) {
                responses.addApiResponse("400", problem("Validation failed; detail names the offending field"));
            }
            return operation;
        };
    }

    /** Keep the components block tidy: the ProblemDetail schema is registered once, in the bean above. */
    @Bean
    OpenApiCustomizer jiltsaOpenApiCustomizer() {
        return openApi -> openApi.getPaths().values().forEach(path ->
                path.readOperations().forEach(op -> op.getResponses().forEach((code, r) -> {
                    if (code.startsWith("2") && r.getDescription() == null) r.setDescription("OK");
                })));
    }

    private static String moduleOf(HandlerMethod handler) {
        String pkg = handler.getBeanType().getPackageName();
        String rest = pkg.startsWith(ROOT_PACKAGE) ? pkg.substring(ROOT_PACKAGE.length()) : pkg;
        int dot = rest.indexOf('.');
        return dot < 0 ? rest : rest.substring(0, dot);
    }

    /** Replace whatever springdoc generated for a Pageable with the parameters PageRequestResolver reads. */
    private static void documentPaging(Operation operation, HandlerMethod handler) {
        Optional<MethodParameter> pageable = Arrays.stream(handler.getMethodParameters())
                .filter(p -> Pageable.class.isAssignableFrom(p.getParameterType())).findFirst();
        if (pageable.isEmpty()) return;

        PageableDefault defaults = pageable.get().getParameterAnnotation(PageableDefault.class);
        int size = defaults != null ? defaults.size() : 10;
        String sortBy = defaults != null && defaults.sort().length > 0 ? defaults.sort()[0] : null;
        String direction = defaults != null ? defaults.direction().name().toLowerCase() : Sort.Direction.ASC.name().toLowerCase();

        List<Parameter> params = new ArrayList<>(Optional.ofNullable(operation.getParameters()).orElse(List.of()));
        params.removeIf(p -> List.of("page", "size", "sort", "pageable",
                PageRequestResolver.SIZE_PARAMETER, PageRequestResolver.SORT_BY_PARAMETER,
                PageRequestResolver.SORT_DIRECTION_PARAMETER).contains(p.getName()));
        params.add(query("page", "Zero-based page index", new IntegerSchema()._default(0).minimum(java.math.BigDecimal.ZERO)));
        params.add(query(PageRequestResolver.SIZE_PARAMETER, "Page size", new IntegerSchema()._default(size).minimum(java.math.BigDecimal.ONE)));
        params.add(query(PageRequestResolver.SORT_BY_PARAMETER, "Field to sort by", new StringSchema()._default(sortBy)));
        params.add(query(PageRequestResolver.SORT_DIRECTION_PARAMETER, "Sort direction",
                new StringSchema()._enum(List.of("asc", "desc"))._default(direction)));
        operation.setParameters(params);
    }

    private static Parameter query(String name, String description, Schema<?> schema) {
        return new QueryParameter().name(name).description(description).required(false).schema(schema);
    }

    private static boolean hasValidatedBody(HandlerMethod handler) {
        return Arrays.stream(handler.getMethodParameters()).anyMatch(p ->
                p.hasParameterAnnotation(RequestBody.class)
                        && (p.hasParameterAnnotation(Valid.class) || p.getGenericParameterType().getTypeName().contains("List<")));
    }

    private static ApiResponse problem(String description) {
        return new ApiResponse().description(description).content(new Content().addMediaType(PROBLEM_JSON,
                new MediaType().schema(new Schema<>().$ref("#/components/schemas/" + PROBLEM))));
    }
}
