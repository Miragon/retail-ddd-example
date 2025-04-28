plugins {
    `kotlin-dsl`
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.versions.java.get()))
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.versions.java.get()))
    }
}

dependencies {
    implementation(libs.kotlinGradlePlugin)
    implementation(libs.kotlinAllOpenPlugin)
    implementation(libs.springBootGradlePlugin)
    implementation(libs.springDependencyManagementPlugin)
}
