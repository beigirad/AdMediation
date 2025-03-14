// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.kotlinxSerialization) apply false
    alias(libs.plugins.kover)
}

// run ./gradlew koverHtmlReport to generate an aggregated test coverage result for whole project
kover {
    merge {
        val ignoredModules = setOf("example")
        allProjects { project -> project.name !in ignoredModules }
    }
}