// `pluginManagement` configures the repository sources for Gradle plugins.
// Gradle will look for the plugins declared in the `plugins {}` block from the repositories defined here.
pluginManagement {
    repositories {
        gradlePluginPortal() // Gradle 官方插件门户。
        mavenCentral()       // Maven 中央仓库。
        google()             // Google 的 Maven 仓库，用于存放 Android 相关库和插件。
        maven("https://jitpack.io") // JitPack 仓库，用于轻松构建任何 GitHub/GitLab 项目。

    }
}

// `dependencyResolutionManagement` 用于集中管理所有模块的依赖项仓库。
dependencyResolutionManagement {
    // Set repository mode to FAIL_ON_PROJECT_REPOS to prevent submodules from defining repositories in their own build.gradle files.
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
        maven("https://jitpack.io")
        maven("https://repo.xposed.info") // Alternate Xposed Maven repository for the Xposed API dependency.

    }
}

// Set the root project name.
rootProject.name = "OShin"
// Include the :app module as part of the project build.
include(":app")
