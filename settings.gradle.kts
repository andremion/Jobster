enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Jobster"

include(":androidApp")
include(":shared:domain")
include(":shared:data")
include(":shared:presentation")
include(":shared:ui")
