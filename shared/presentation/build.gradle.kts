plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "PresentationShared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.domain)

            implementation(libs.kotlinx.coroutines)
            implementation(libs.koin.core)
            implementation(libs.andremion.boomerang)
        }
    }
}
