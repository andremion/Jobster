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
        versionCode = 3
        versionName = "0.1.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    val releaseKeyStoreFile = properties["releaseKeyStoreFile"]?.toString()
        ?: System.getenv("releaseKeyStoreFile")
    val releaseKeyStoreAlias = properties["releaseKeyStoreAlias"]?.toString()
        ?: System.getenv("releaseKeyStoreAlias")
    val releaseKeyStorePassword = properties["releaseKeyStorePassword"]?.toString()
        ?: System.getenv("releaseKeyStorePassword")
    val releaseKeysProvided =
        releaseKeyStoreFile != null && releaseKeyStoreAlias != null && releaseKeyStorePassword != null

    signingConfigs {
        if (releaseKeysProvided) {
            create("release") {
                storeFile = file(releaseKeyStoreFile)
                storePassword = releaseKeyStorePassword
                keyAlias = releaseKeyStoreAlias
                keyPassword = releaseKeyStorePassword
            }
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
        release {
            if (releaseKeysProvided) {
                signingConfig = signingConfigs.getByName("release")
            }
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
