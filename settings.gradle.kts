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
        mavenLocal()
        maven { url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots") }
    }
}

rootProject.name = "Jobster"

include(":composeApp")
include(":shared:domain")
include(":shared:data")
include(":shared:presentation")
