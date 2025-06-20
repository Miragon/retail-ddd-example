plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.springDependencyManagement)
    alias(libs.plugins.kotlinPluginJpa)
    alias(libs.plugins.kotlinSpringBoot)
    alias(libs.plugins.springBoot)
}

dependencies {
    implementation(libs.bundles.webService)
    implementation(libs.bundles.openApi)
    testImplementation(libs.bundles.commonTest)
    testRuntimeOnly(libs.h2)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

kotlin {
    jvmToolchain(21)
}