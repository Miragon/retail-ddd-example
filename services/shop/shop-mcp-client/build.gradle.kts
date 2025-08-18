plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.springDependencyManagement)
    alias(libs.plugins.kotlinSpringBoot)
    alias(libs.plugins.springBoot)
}

dependencies {
    implementation(libs.bundles.webService)
    implementation(libs.springAiMcpServer)
    testImplementation(libs.bundles.commonTest)
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

kotlin {
    jvmToolchain(21)
}