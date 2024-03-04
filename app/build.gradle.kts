@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
    kotlin("kapt")
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.codesthetic.rickandmortyapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.codesthetic.rickandmortyapp"
        minSdk = 24
        targetSdk = 33
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Module
    implementation(project(":engine-core"))
    implementation(project(":feature-characters"))
    implementation(project(":feature-episodes"))
    implementation(project(":feature-locations"))
    implementation(project(":feature-appsetting"))

    // Hilt
    api(libs.hilt)
    kapt(libs.hilt.compiler)

    // Room
    implementation(libs.bundles.room.database)
    kapt(libs.room.compiler)

    // Navigation Fragments
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
}
