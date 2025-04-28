// The code in this file is a convention plugin - a Gradle mechanism for sharing reusable build logic.
// `buildSrc` is a Gradle-recognized directory and every plugin there will be easily available in the rest of the build.
package buildsrc.convention

import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    // Classes annotated with @Configuration, @Controller, @RestController, @Service or @Repository are automatically
    // opened
    // https://kotlinlang.org/docs/reference/compiler-plugins.html#spring-support
    kotlin("plugin.spring")

    // Apply the Kotlin JVM plugin to add support for Kotlin in JVM projects.
    kotlin("jvm")

    // Allows to package executable jar or war archives, run Spring Boot applications, and use the dependency management
    // https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/html/
    id("org.springframework.boot")

    // This plugin enables the spring dependency management
    id("io.spring.dependency-management")
}

tasks.withType<Test>().configureEach {
    // Configure all test Gradle tasks to use JUnitPlatform.
    useJUnitPlatform()

    // Log information about all test results, not only the failed ones.
    testLogging {
        events(
            TestLogEvent.FAILED,
            TestLogEvent.PASSED,
            TestLogEvent.SKIPPED
        )
    }
}
