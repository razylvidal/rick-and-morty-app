plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    kotlin("android")
}

android {
    namespace = "com.codesthetic.shared.features"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    implementation(libs.material)

    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.androidcompiler)

    api(libs.flexiadapter)
    api(libs.flexiadapter.ui)

    implementation(libs.coil)

    api(project(":engine-core"))
    api(project(":shared:theme"))
    api(project(":shared:flexi"))
    api(project(":shared:utils-android"))
}
