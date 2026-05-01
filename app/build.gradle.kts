// Declare Gradle plugins required for project build.
plugins {
    // Reference plugins through Gradle Version Catalog aliases for centralized management.
    alias(libs.plugins.android.application) // Android application plugin for building .apk files.
    alias(libs.plugins.kotlin.android)      // Provide Kotlin support on the Android platform.
    alias(libs.plugins.ksp)                 // Kotlin Symbol Processing (KSP) plugin for compile-time code generation.
    alias(libs.plugins.kotlin.compose)      // Jetpack Compose compiler plugin for @Composable annotations.
    id("com.google.dagger.hilt.android")
}

/**
 * Git version information provider.
 *
 * Use Gradle Provider API for lazy configuration of build information.
 * `git` commands are executed only when the configuration property (e.g. `versionCode`) is actually needed,
 * to optimize Gradle performance in the configuration phase.
 */
// Get the short Git commit hash.
val gitCommitHashProvider = providers.exec {
    commandLine("git", "rev-parse", "--short", "HEAD")
}.standardOutput.asText.map { it.trim() }

// Get the total number of commits from repository root to current HEAD.
val gitCommitCountProvider = providers.exec {
    commandLine("git", "rev-list", "--count", "HEAD")
}.standardOutput.asText.map { it.trim() }

// Core Android project configuration.
android {
    namespace = "com.suqi8.oshin" // Define the application package name used to generate the R class and Manifest.
    compileSdk = 36               // Specify the Android API version used to compile the app.

    // Default configuration applied to all build variants.
    defaultConfig {
        applicationId = "com.suqi8.oshin" // Unique identifier for the app on device and app stores.
        minSdk = 33                       // Minimum Android API level the app can run on.
        targetSdk = 36                    // Target Android API level the app is designed and tested against.

        // Dynamically set version information.
        // .getOrElse() provides a fallback value for builds outside Git environments.
        versionCode = gitCommitCountProvider.map { it.toInt() }.getOrElse(1)
        versionName = gitCommitCountProvider.zip(gitCommitHashProvider) { count, hash ->
            "16.2.$count.$hash" // Version name format: major.minor.commit_count.commit_hash
        }.getOrElse("16.local") // Default version name used when Git is unavailable.

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" // Specify the instrumentation test runner.
        vectorDrawables {
            useSupportLibrary = true // Enable vector drawable support for devices below API 21.
        }
    }

    // --- ABI split configuration ---
    // This block instructs Gradle to generate separate APKs for different CPU architectures.
    splits {
        abi {
            isEnable = true          // 1. Enable ABI splitting
            reset()                // 2. Reset default settings (e.g. x86, mips)
            include("arm64-v8a")     // 3. Include only 64-bit v8a architecture
            isUniversalApk = false     // 4. Do not generate a universal/all APK
        }
    }

    // Configure APK output file name.
    // Note: this uses the deprecated `applicationVariants.all` API.
    // This is for compatibility with the current build environment and to ensure APK filename customization stability.
    applicationVariants.all {
        val variant = this
        variant.outputs.all {
            val outputImpl = this as com.android.build.gradle.internal.api.BaseVariantOutputImpl
            val name = "OShin"
            // Get the ABI (Application Binary Interface) name from the output filter.
            val abi = outputImpl.filters.find { it.filterType == "ABI" }?.identifier ?: "all"
            val version = variant.versionName
            val versionCode = variant.versionCode
            val outputFileName = "${name}_${abi}_v${version}(${versionCode}).apk"
            outputImpl.outputFileName = outputFileName
        }
    }

    // Configure app signing information.
    signingConfigs {
        val keystoreFile = System.getenv("KEYSTORE_PATH")
        val isCiBuild = keystoreFile != null

        // If CI/CD environment variables are detected, create a signing config for continuous integration.
        if (isCiBuild) {
            create("ci") {
                storeFile = file(keystoreFile)
                storePassword = System.getenv("KEYSTORE_PASSWORD")
                keyAlias = System.getenv("KEY_ALIAS")
                keyPassword = System.getenv("KEY_PASSWORD")
                enableV4Signing = true // 启用 APK 签名方案 v4，以支持增量安装等优化。
            }
        }
        // Create a generic "release" signing config for local builds or non-CI environments.
        create("release") {
            enableV4Signing = true
        }
    }

    // Configure different build types such as "release" and "debug".
    buildTypes {
        release {
            val keystoreFile = System.getenv("KEYSTORE_PATH")
            val isCiBuild = keystoreFile != null
            // Choose the signing config based on whether CI environment variables exist.
            signingConfig = signingConfigs.getByName(if (isCiBuild) "ci" else "debug")

            // Generate a constant in BuildConfig.java via `buildConfigField`.
            val buildTag = if (isCiBuild) "CI Build" else "Release"
            buildConfigField("String", "BUILD_TYPE_TAG", "\"$buildTag\"")

            isMinifyEnabled = true      // Enable R8/ProGuard for code shrinking, optimization, and obfuscation.
            isShrinkResources = true    // Enable resource shrinking to remove unused resources.
            isDebuggable = false        // Disable debugging for release builds.
            isJniDebuggable = false     // Disable JNI (C/C++) debugging.
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            buildConfigField("String", "BUILD_TYPE_TAG", "\"Debug\"")
        }
    }

    // Java/Kotlin compilation options.
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17 // Set Java source compatibility level.
        targetCompatibility = JavaVersion.VERSION_17 // Set the target JVM version for generated Java bytecode.
    }

    // Configure Kotlin compiler options.
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17) // Set the JVM target version for Kotlin compilation output.
            freeCompilerArgs.addAll(
                "-Xno-param-assertions",
                "-Xno-call-assertions",
                "-Xno-receiver-assertions"
            )
        }
    }

    // Enable or disable specific build features.
    buildFeatures {
        buildConfig = true // Enable automatic generation of `BuildConfig.java`.
        viewBinding = true // Enable view binding support.
        compose = true     // Enable Jetpack Compose support.
    }

    // Jetpack Compose compiler configuration.
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    // APK packaging configuration.
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "DebugProbesKt.bin"
            excludes += "kotlin-tooling-metadata.json"
        }
    }

    // Android resource handling configuration.
    androidResources {
        ignoreAssetsPattern = "!*.ttf:!*.json:!*.bin"
        noCompress += listOf("zip", "txt", "raw", "png")
    }

    kotlin {
        jvmToolchain(17)
        compilerOptions {
            freeCompilerArgs.addAll(
                "-Xcontext-parameters"
            )
        }
    }

    // Lint static analysis configuration.
    lint {
        baseline = file("lint-baseline.xml") // Set a baseline file to ignore existing Lint issues.
    }
}

// Dependency declaration block
dependencies {
    // ------------------- AndroidX & Jetpack core libraries -------------------
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.palette.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.hilt.android)
    implementation(libs.androidx.foundation.layout)
    ksp(libs.hilt.android.compiler)

    // ------------------- Jetpack Compose UI -------------------
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    debugImplementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    // ------------------- Compose ecosystem third-party libraries -------------------
    //implementation(libs.accompanist.flowlayout)
    implementation(libs.airbnb.lottie.compose)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    //implementation(libs.haze)
    //implementation(libs.shimmer.compose)
    //implementation(libs.toolbar.compose)
    //implementation(libs.expandablebottombar)
    //implementation(libs.neumorphism.compose)
    implementation(libs.capsule)
    implementation(libs.multiplatform.markdown.renderer.android)
    implementation(libs.multiplatform.markdown.renderer.coil3)
    implementation(libs.multiplatform.markdown.renderer.code)

    // ------------------- 底层与工具库 -------------------
    implementation(libs.luckypray.dexkit)
    implementation(libs.xxpermissions)
    implementation(libs.squareup.okhttp)
    implementation(libs.gson)
    //implementation(libs.drawabletoolbox)
    implementation(libs.miuix)
    //implementation(libs.mmkv)

    // ------------------- Hook API 相关 -------------------
    implementation(libs.ezxhelper)
    compileOnly(project(":xposed-api-stubs"))
    implementation(libs.yukihook.api)
    ksp(libs.yukihook.ksp.xposed)
    implementation(libs.kavaref.core)
    implementation(libs.kavaref.extension)

    // ------------------- Room 数据库 -------------------
    runtimeOnly(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    // ------------------- Umeng (友盟) SDK -------------------
    implementation(libs.umeng.common)
    implementation(libs.umeng.asms)
    implementation(libs.umeng.uyumao)
    implementation(libs.union)

    // ------------------- 测试相关库 -------------------
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
}
