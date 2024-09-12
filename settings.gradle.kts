pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
//        maven("https://plugins.gradle.org/m2/")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
        maven("https://jitpack.io")

//        maven("https://jitpack.io")
    }
}

rootProject.name = "Rick And Morty App"
include(":app")
include(":engine-core")
include(":feature-splash")
include(":feature-characters")
include(":feature-episodes")
include(":feature-appsetting")
include(":shared:features")
include(":shared:theme")
include(":shared:utils-android")
include(":shared:flexi")
