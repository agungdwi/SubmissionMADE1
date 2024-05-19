plugins {
    alias(libs.plugins.android.dynamic.feature)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-parcelize")
    id ("kotlin-kapt")
}
android {
    namespace = "com.example.favorites"
    compileSdk = 34

    defaultConfig {

        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        viewBinding = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

}


dependencies {
    implementation(project(":core"))
    implementation(project(":app"))
}