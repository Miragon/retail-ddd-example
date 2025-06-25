package de.dm.elvis.common.test.archunit.rules

import com.tngtech.archunit.core.domain.JavaClass
import com.tngtech.archunit.core.domain.JavaType
import com.tngtech.archunit.lang.ArchCondition
import com.tngtech.archunit.lang.ConditionEvents
import com.tngtech.archunit.lang.SimpleConditionEvent

object InterfaceImplementationConditions {

    /**
     * Ensures that a class implements exactly one interface from a specified package.
     * A typical use-case is to enforce that application services implement exactly one inbound port.
     * @param packagePattern The package pattern the interface should reside in
     * @return ArchCondition that can be used in `.should(...)`
     */
    fun implementExactlyOneInterfaceFrom(
        packagePattern: String
    ): ArchCondition<JavaClass> {
        return object : ArchCondition<JavaClass>("implement exactly one interface from $packagePattern") {
            override fun check(item: JavaClass, events: ConditionEvents) {
                val clazzName = item.simpleName
                val useCasesOrQueries = item.interfaces.filter { it.isLocatedInPackage(packagePattern) }
                if (useCasesOrQueries.size != 1) {
                    val nrOfImpls = useCasesOrQueries.size
                    val error = "$clazzName should implement exactly one interface, but implements '$nrOfImpls'"
                    events.add(SimpleConditionEvent.violated(item, error))
                } else {
                    events.add(SimpleConditionEvent.satisfied(item, "${item.simpleName} is OK"))
                }
            }
        }
    }

    private fun JavaType.isLocatedInPackage(packagePattern: String): Boolean {
        return this.toErasure().packageName.contains(packagePattern)
    }

}