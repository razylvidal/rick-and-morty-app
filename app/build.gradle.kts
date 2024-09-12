import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.firebase.appDistribution)
    alias(libs.plugins.google.services)
    alias(libs.plugins.ksp)
//    kotlin("kapt")
    kotlin("plugin.parcelize")
}

android {
    namespace = "com.codesthetic.rickandmortyapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.codesthetic.rickandmortyapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            configure<CrashlyticsExtension> {
                mappingFileUploadEnabled = false // to disable mapping file uploads (default=true if minifying)
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    kotlin {
        jvmToolchain(17)
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    lint {
        checkReleaseBuilds = false
        checkDependencies = true
        abortOnError = true
        warningsAsErrors = false
        ignoreWarnings = false
        baseline = file("lint-baseline.xml")
    }

    packagingOptions.resources {
        excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.test.runner)
    androidTestImplementation(libs.test.rules)

    // Module
    implementation(project(":feature-splash"))
    implementation(project(":feature-characters"))
    implementation(project(":feature-episodes"))
    implementation(project(":feature-appsetting"))

    // Dagger Hilt
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.androidcompiler)

//    // For instrumentation tests
//    androidTestImplementation(libs.dagger.hilt.androidtesting)
//    kaptAndroidTest(libs.dagger.hilt.compiler)
//
//    // For local unit tests
//    testImplementation(libs.dagger.hilt.androidtesting)
//    kaptTest(libs.dagger.hilt.compiler)

    // Room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)

    // Navigation Fragments
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    // Chucker
    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker.noop)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
}
