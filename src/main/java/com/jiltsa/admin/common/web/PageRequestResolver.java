package com.jiltsa.admin.common.web;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortArgumentResolver;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Resolves {@code Pageable} controller arguments from the query parameters this
 * API has always exposed: {@code page}, {@code elements}, {@code sortBy} and
 * {@code sortDirection}. Per-endpoint defaults come from {@code @PageableDefault}.
 */
public class PageRequestResolver extends PageableHandlerMethodArgumentResolver {
    public static final String SIZE_PARAMETER = "elements";
    public static final String SORT_BY_PARAMETER = "sortBy";
    public static final String SORT_DIRECTION_PARAMETER = "sortDirection";

    public PageRequestResolver() {
        super(new SortByAndDirectionResolver());
        setSizeParameterName(SIZE_PARAMETER);
    }

    /** {@code sortBy=date&sortDirection=desc}; anything but "desc" sorts ascending. */
    static class SortByAndDirectionResolver implements SortArgumentResolver {
        @Override
        public boolean supportsParameter(MethodParameter parameter) {
            return Sort.class.equals(parameter.getParameterType());
        }

        @Override
        public Sort resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                    NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
            String sortBy = webRequest.getParameter(SORT_BY_PARAMETER);
            if (!StringUtils.hasText(sortBy)) {
                return Sort.unsorted();
            }
            Sort.Direction direction = "desc".equalsIgnoreCase(webRequest.getParameter(SORT_DIRECTION_PARAMETER))
                    ? Sort.Direction.DESC : Sort.Direction.ASC;
            return Sort.by(direction, sortBy);
        }
    }
}
