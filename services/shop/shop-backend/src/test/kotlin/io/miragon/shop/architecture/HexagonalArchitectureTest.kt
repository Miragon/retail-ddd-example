package io.miragon.shop.architecture

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import com.tngtech.archunit.library.Architectures
import de.dm.elvis.common.test.archunit.rules.InterfaceImplementationConditions.implementExactlyOneInterfaceFrom
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HexagonalArchitectureTest {

    private val rootPackage = "io.miragon.shop"

    private var allClasses = ClassFileImporter()
        .importPackages(rootPackage)

    private val productionClasses = ClassFileImporter()
        .withImportOption(DoNotIncludeTests())
        .importPackages(rootPackage)

    @Test
    fun `hexagonal architecture should be respected`() {
        val architectureRule = Architectures.layeredArchitecture()
            .consideringOnlyDependenciesInLayers()
            .layer("Domain").definedBy("..domain..")
            .layer("In-Ports").definedBy("..application.port.inbound..")
            .layer("Out-Ports").definedBy("..application.port.outbound..")
            .layer("In-Adapters").definedBy("..adapter.inbound..")
            .layer("Out-Adapters").definedBy("..adapter.outbound..")
            .layer("Application").definedBy("..application.service..")
            .whereLayer("In-Ports").mayOnlyBeAccessedByLayers("Application", "In-Adapters")
            .whereLayer("Out-Ports").mayOnlyBeAccessedByLayers("Application", "Out-Adapters")
            .whereLayer("In-Adapters").mayNotBeAccessedByAnyLayer()
            .whereLayer("Out-Adapters").mayNotBeAccessedByAnyLayer()
            .whereLayer("Application").mayNotBeAccessedByAnyLayer()
            .whereLayer("Domain").mayNotAccessAnyLayer()
            .ensureAllClassesAreContainedInArchitectureIgnoring(
                rootPackage, "$rootPackage.architecture"
            )

        architectureRule.check(allClasses)
    }

    @Nested
    inner class GeneralPortTests {

        @Test
        fun `application ports should be interfaces`() {
            ArchRuleDefinition.classes()
                .that().resideInAPackage("..application.port.inbound..")
                .or().resideInAPackage("..application.port.outbound..")
                .and().areTopLevelClasses()
                .should().beInterfaces()
                .because("All ports should be defined as interfaces")
                .check(allClasses)
        }

        @Test
        fun `in ports should either be use-cases or queries`() {
            ArchRuleDefinition.classes()
                .that().resideInAPackage("..application.port.inbound..")
                .should().haveSimpleNameEndingWith("UseCase")
                .orShould().haveSimpleNameEndingWith("Query")
                .because("Inbound ports should be defined as use-cases or queries")
                .check(productionClasses)
        }
    }

    @Nested
    inner class ApplicationTests {

        @Test
        fun `application services should be named with Service suffix`() {
            ArchRuleDefinition.classes()
                .that().resideInAPackage("..application.service..")
                .should().haveSimpleNameEndingWith("Service")
                .because("Application services should be named with Service suffix")
                .check(productionClasses)
        }

        @Test
        fun `application service should implement exactly one use-case`() {
            ArchRuleDefinition.classes()
                .that().resideInAPackage("..application.service..")
                .should(implementExactlyOneInterfaceFrom("application.port.inbound"))
                .because("application services must implement at least one inbound port")
                .check(productionClasses)
        }

        @Test
        fun `application service should not depends on other application services`() {
            ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..application.service..")
                .should().dependOnClassesThat().resideInAPackage("..application.service..")
                .because("Application services should not depend on each other")
                .check(productionClasses)
        }
    }
    
}