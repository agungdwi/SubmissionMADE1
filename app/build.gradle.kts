plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("androidx.navigation.safeargs")
}

android {
    namespace = "com.example.moviedb"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.moviedb"
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
    }
    dynamicFeatures += setOf(":favorites")
}

dependencies {
    implementation(project(":core"))
    implementation (libs.androidx.core.splashscreen)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)
//    implementation(libs.androidx.activity)
//    implementation(libs.androidx.constraintlayout)

//    implementation(libs.androidx.room.runtime)
//    implementation(libs.retrofit)
//    implementation(libs.converter.gson)
//    implementation(libs.logging.interceptor)
//    implementation(libs.androidx.room.ktx)
//    implementation (libs.insert.koin.koin.core)
//    implementation (libs.insert.koin.koin.android)
//    implementation(libs.androidx.room.paging)
////    implementation (libs.koin.android.viewmodel)
//    kapt(libs.androidx.room.compiler)
//
//
//    annotationProcessor(libs.compiler)
}