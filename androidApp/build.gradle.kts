plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.compose)
}

android {
    namespace = "io.github.andremion.jobster"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "io.github.andremion.jobster.android"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        val default = "NOT_PROVIDED"
        val releaseKeyStoreFile: String? by project
        val releaseKeyStoreAlias: String? by project
        val releaseKeyStorePassword: String? by project
        create("release") {
            storeFile = file(releaseKeyStoreFile ?: System.getenv("releaseKeyStoreFile") ?: default)
            storePassword = releaseKeyStorePassword ?: System.getenv("releaseKeyStorePassword") ?: default
            keyAlias = releaseKeyStoreAlias ?: System.getenv("releaseKeyStoreAlias") ?: default
            keyPassword = releaseKeyStorePassword ?: System.getenv("releaseKeyStorePassword") ?: default
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(projects.shared.ui)

    debugImplementation(compose.uiTooling)
    debugImplementation(compose.preview)
}
