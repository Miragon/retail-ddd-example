plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.springDependencyManagement)
    alias(libs.plugins.kotlinPluginJpa)
    alias(libs.plugins.kotlinSpringBoot)
}

dependencies {
    implementation(libs.bundles.webService)
    implementation(libs.bundles.database)
    implementation(libs.bundles.openApi)
    testImplementation(libs.bundles.commonTest)
}