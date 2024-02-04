plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library)
    alias(libs.plugins.sqldelight)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "SharedData"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.domain)

            implementation(libs.kotlinx.coroutines)
            implementation(libs.kotlinx.serialization.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.koin.core)
            implementation(libs.sqldelight.coroutines)
            implementation(libs.ktor.client.core)
            implementation(libs.ksoup)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.android)
            implementation(libs.google.generativeai)
            implementation(libs.sqldelight.android)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.ios)
            implementation(libs.sqldelight.native)
            // FIXME This is just to fix a Kotlin/Native compilation issue until Koin has updated their dependencies.
            //  https://github.com/cashapp/sqldelight/issues/4357#issuecomment-1839905700
            implementation("co.touchlab:stately-common:2.0.5")
        }
    }
}

android {
    namespace = "io.github.andremion.jobster.data"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        val geminiApiKey = project.property("geminiApiKey") ?: System.getenv("GEMINI_API_KEY") ?: "NOT_PROVIDED"
        buildConfigField("String", "GeminiApiKey", "\"$geminiApiKey\"")
    }
}

sqldelight {
    databases {
        create("JobsterDatabase") {
            packageName.set("io.github.andremion.jobster.data.local.db")
        }
    }
}
