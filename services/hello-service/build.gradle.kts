plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.springDependencyManagement)
    alias(libs.plugins.kotlinSpringBoot)
}

dependencies {
    implementation(libs.springBootWeb)
    implementation(libs.springBootActuator)
    testImplementation(libs.springBootTest)
}