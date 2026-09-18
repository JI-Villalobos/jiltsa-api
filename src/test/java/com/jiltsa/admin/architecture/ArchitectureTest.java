package com.jiltsa.admin.architecture;

import com.tngtech.archunit.core.domain.Dependency;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noMethods;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

/**
 * The module layout this codebase settled on:
 * <pre>
 *   com.jiltsa.admin.&lt;module&gt;.controller  -> domain.service -> persistence.{repository,mapper,entity}
 *   com.jiltsa.admin.&lt;module&gt;.domain.dto   (plain data, no persistence types)
 * </pre>
 * Modules may use each other's domain layer; another module's persistence is
 * reachable only from JPA entities (associations such as Accounting -> Branch).
 */
@AnalyzeClasses(packages = ArchitectureTest.ROOT, importOptions = ImportOption.DoNotIncludeTests.class)
class ArchitectureTest {
    static final String ROOT = "com.jiltsa.admin";

    @ArchTest
    static final ArchRule modules_are_free_of_cycles =
            slices().matching(ROOT + ".(*)..").should().beFreeOfCycles();

    @ArchTest
    static final ArchRule only_entities_may_reach_another_modules_persistence =
            classes().that().resideOutsideOfPackage("..persistence.entity..")
                    .should(notDependOnAnotherModulesPersistence())
                    .because("modules talk to each other through their domain layer");

    @ArchTest
    static final ArchRule controllers_do_not_depend_on_persistence =
            noClasses().that().resideInAPackage("..controller..")
                    .should().dependOnClassesThat().resideInAPackage("..persistence..");

    @ArchTest
    static final ArchRule dtos_do_not_depend_on_persistence =
            noClasses().that().resideInAPackage("..domain.dto..")
                    .should().dependOnClassesThat().resideInAPackage("..persistence..");

    @ArchTest
    static final ArchRule lower_layers_do_not_depend_on_controllers =
            noClasses().that().resideInAnyPackage("..domain..", "..persistence..")
                    .should().dependOnClassesThat().resideInAPackage("..controller..");

    @ArchTest
    static final ArchRule services_own_their_transactions =
            classes().that().areAnnotatedWith(Service.class)
                    .and().resideOutsideOfPackage("..security..")
                    .should().beAnnotatedWith(Transactional.class)
                    .because("open-in-view is off, so lazy associations must be loaded inside a service transaction");

    @ArchTest
    static final ArchRule only_springs_transactional_is_used =
            noClasses().should().dependOnClassesThat().haveFullyQualifiedName("jakarta.transaction.Transactional");

    @ArchTest
    static final ArchRule controllers_do_not_return_optional =
            noMethods().that().areDeclaredInClassesThat().resideInAPackage("..controller..")
                    .should().haveRawReturnType(Optional.class)
                    .because("a missing resource is a 404, not a 200 with a null body");

    @ArchTest
    static final ArchRule no_field_injection =
            noFields().should().beAnnotatedWith(Autowired.class);

    /** The first package segment after the root: com.jiltsa.admin.<module>... */
    private static String moduleOf(JavaClass clazz) {
        String rest = clazz.getPackageName().substring(ROOT.length() + 1);
        int dot = rest.indexOf('.');
        return dot < 0 ? rest : rest.substring(0, dot);
    }

    private static ArchCondition<JavaClass> notDependOnAnotherModulesPersistence() {
        return new ArchCondition<>("not depend on another module's persistence package") {
            @Override
            public void check(JavaClass origin, ConditionEvents events) {
                for (Dependency dependency : origin.getDirectDependenciesFromSelf()) {
                    JavaClass target = dependency.getTargetClass();
                    boolean crossModulePersistence = target.getPackageName().startsWith(ROOT + ".")
                            && target.getPackageName().contains(".persistence")
                            && !moduleOf(target).equals(moduleOf(origin));
                    if (crossModulePersistence) {
                        events.add(SimpleConditionEvent.violated(dependency, dependency.getDescription()));
                    }
                }
            }
        };
    }
}
