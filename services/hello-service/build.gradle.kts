plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.springDependencyManagement)
    alias(libs.plugins.kotlinPluginJpa)
    alias(libs.plugins.kotlinSpringBoot)
    alias(libs.plugins.springBoot)
}

dependencies {
    implementation(libs.springBootWeb)
    implementation(libs.springBootActuator)
    testImplementation(libs.bundles.commonTest)
}