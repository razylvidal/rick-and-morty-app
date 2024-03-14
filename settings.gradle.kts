pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
//        maven("https://plugins.gradle.org/m2/")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
//        maven("https://jitpack.io")
    }
}

rootProject.name = "Rick And Morty App"
include(":app")
include(":engine-core")
include(":feature-characters")
include(":feature-locations")
include(":feature-episodes")
include(":feature-appsetting")
include(":shared:theme")
include(":shared:utils-android")
include(":shared:flexi")
