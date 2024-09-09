plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.ksp)
    kotlin("plugin.parcelize")
    kotlin("android")
}

android {
    namespace = "com.codesthetic.feature.characters"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.constraintlayout)

    // Dagger Hilt
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.androidcompiler)

    implementation(libs.coil)

    api(project(":engine-core"))
    api(project(":shared:theme"))
    implementation(project(":shared:flexi"))
    implementation(project(":shared:utils-android"))
    implementation(libs.lottie)
}
