plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    kotlin("android")
}

android {
    namespace = "com.codesthetic.engine.core"

    room {
        schemaDirectory("$projectDir/schemas")
    }

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        javaCompileOptions {
            annotationProcessorOptions {
                arguments +=
                    mapOf(
//                        "room.schemaLocation" to "$projectDir/schemas",
                        "room.incremental" to "true",
                        "room.expandProjection" to "true"
                    )
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Dagger Hilt
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.androidcompiler)

    api(libs.retrofit)
    api(libs.gson.converter)
    api(libs.retrofit.converter.gson)

    // Room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)

    api(libs.timber)

    implementation(libs.paging.common)
    implementation(libs.paging.runtime)
}
