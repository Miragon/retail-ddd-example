package io.miragon.shop.architecture.conditions

import com.tngtech.archunit.core.domain.JavaClass
import com.tngtech.archunit.core.domain.JavaParameter
import com.tngtech.archunit.lang.ArchCondition
import com.tngtech.archunit.lang.ConditionEvents
import com.tngtech.archunit.lang.SimpleConditionEvent

object UseCaseDependencyConditions {

    /**
     * Ensures that a class does not depend on multiple classes from the application package.
     * This enforces that an adapter can only fulfill one use-case.
     * Moreover, it prevents performance issues by ensuring only one transaction is open,
     * as transactions are managed on the application layer.
     */
    val onlyFulfilOneUseCase = object : ArchCondition<JavaClass>("only fulfil one use case") {

        override fun check(javaClass: JavaClass, events: ConditionEvents) {
            val applicationDependencies = this.findUseCaseDependenciesInConstructor(javaClass)
            if (applicationDependencies.size > 1) {
                val message = "${javaClass.name} depends on more than one use-case: $applicationDependencies"
                events.add(SimpleConditionEvent.violated(this.javaClass, message))
            }
        }

        /**
         * Finds all constructor parameters that are from the application package,
         * which typically represent use case dependencies.
         */
        private fun findUseCaseDependenciesInConstructor(javaClass: JavaClass): List<JavaParameter> {
            return javaClass.constructors
                .flatMap { constructor -> constructor.parameters }
                .filter { param -> param.rawType.packageName.contains("application.port.inbound") }
        }
    }

}
