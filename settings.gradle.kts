// `pluginManagement` configures the repository sources for Gradle plugins.
// Gradle will look for the plugins declared in the `plugins {}` block from the repositories defined here.
pluginManagement {
    repositories {
        gradlePluginPortal() // Official Gradle plugin portal.
        mavenCentral()       // Maven Central repository.
        google()             // Google's Maven repository for Android-related libraries and plugins.
        maven("https://jitpack.io") // JitPack repository for easily building any GitHub/GitLab project.

    }
}

// `dependencyResolutionManagement` centrally manages dependency repositories for all modules.
dependencyResolutionManagement {
    // Set repository mode to FAIL_ON_PROJECT_REPOS to prevent submodules from defining repositories in their own build.gradle files.
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
        maven("https://jitpack.io")
        maven("https://api.xposed.info/") // Xposed Maven repository for the Xposed API dependency.

    }
}

// Set the root project name.
rootProject.name = "OShin"
// Include the :app module as part of the project build.
include(":app")
