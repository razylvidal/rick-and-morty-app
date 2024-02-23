pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Rick And Morty App"
include(":app")
include(":engine-core")
include(":feature-characters")
include(":feature-locations")
include(":feature-episodes")
include(":feature-appsetting")
