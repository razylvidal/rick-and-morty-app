plugins {
    alias(libs.plugins.android.library)
    kotlin("android")
}

android {
    namespace = "com.codesthetic.shared.utils.android"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.timber)
}
