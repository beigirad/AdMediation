plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlinxSerialization)
}

android {
    namespace = "ir.beigirad.admediation"
    compileSdk = 35

    defaultConfig {
        minSdk = 19

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.ktorCore)
    implementation(libs.ktorOkhttp)
    implementation(libs.ktorOkhttpLogger)
    implementation(libs.ktorNegotiation)
    implementation(libs.ktorKotlinxSerialization)
    implementation(libs.koin)

    testImplementation(libs.junit)
    testImplementation(libs.kotest)
    testImplementation(libs.koinTest)
    testImplementation(libs.mockK)
}