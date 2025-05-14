plugins {
    id("kotlin-spring-boot")
}

dependencies {
    implementation(libs.bundles.webService)
    implementation(libs.bundles.openApi)
    testImplementation(libs.bundles.commonTest)
}