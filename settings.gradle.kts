// `pluginManagement` configures the repository sources for Gradle plugins.
// Gradle will look for the plugins declared in the `plugins {}` block from the repositories defined here.
pluginManagement {
    repositories {
        gradlePluginPortal() // Official Gradle plugin portal.
        mavenCentral()       // Maven Central repository.
        google()             // Google Maven repository for Android libraries and plugins.
        maven("https://jitpack.io") // JitPack repository for GitHub/GitLab artifacts.

    }
}

// `dependencyResolutionManagement` centrally manages repositories for all modules.
dependencyResolutionManagement {
    // Set repository mode to FAIL_ON_PROJECT_REPOS to prevent submodules from defining repositories in their own build.gradle files.
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
        maven("https://jitpack.io")

    }
}

// Set the root project name.
rootProject.name = "OShin"
// Include the :app module as part of the project build.
include(":app")
include(":xposed-api-stubs")
