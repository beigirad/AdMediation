plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlin)
}

android {
    namespace = "ir.beigirad.admediation.example"
    compileSdk = 35

    defaultConfig {
        applicationId = "ir.beigirad.admediation.example"
        minSdk = 23
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":admediation-core"))
    implementation(project(":admediation-tapsell"))
    implementation(project(":admediation-admob"))
    implementation(libs.appcompat)
}