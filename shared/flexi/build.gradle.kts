plugins {
    alias(libs.plugins.android.library)
    kotlin("android")
}

android {
    namespace = "com.codesthetic.shared.flexi"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    implementation(libs.material)

    api(libs.flexiadapter)
    api(libs.flexiadapter.ui)
}
