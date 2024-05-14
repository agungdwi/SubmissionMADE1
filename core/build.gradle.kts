plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-parcelize")
    id ("kotlin-kapt")
}

android {
    namespace = "com.example.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {


    api(libs.material)
    api(libs.androidx.appcompat)
    api(libs.androidx.core.ktx)
    api(libs.androidx.lifecycle.viewmodel.ktx)
    api(libs.androidx.constraintlayout)
    api(libs.androidx.lifecycle.livedata.ktx)
    api(libs.androidx.navigation.fragment.ktx)
    api(libs.androidx.navigation.ui.ktx)
    api(libs.kotlinx.coroutines.core)
    api(libs.kotlinx.coroutines.android)
    api(libs.androidx.swiperefreshlayout)
    api(libs.glide)
    api(libs.androidx.paging.runtime.ktx)
    api(libs.androidx.activity)
    api(libs.junit)
    api(libs.androidx.junit)
    api(libs.androidx.espresso.core)
    api(libs.androidx.navigation.dynamic.features.fragment)

    implementation(libs.androidx.room.runtime)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.room.ktx)
    api (libs.insert.koin.koin.core)
    api (libs.insert.koin.koin.android)
    implementation(libs.androidx.room.paging)

//    implementation (libs.koin.android.viewmodel)
    kapt(libs.androidx.room.compiler)


    annotationProcessor(libs.compiler)


}