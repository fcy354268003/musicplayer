plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(ProjectProperties.compileSdkVersion)
    buildToolsVersion(ProjectProperties.buildToolsVersion)

    defaultConfig {
        minSdkVersion(ProjectProperties.minSdkVersion)
        targetSdkVersion(ProjectProperties.targetSdkVersion)
        applicationId = ProjectProperties.applicationId
        versionCode = ProjectProperties.versionCode
        versionName = ProjectProperties.versionName
        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }

    buildTypes {
        getByName("release") {
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
    buildFeatures {
        dataBinding = true
    }
    kotlinOptions {
        jvmTarget = ProjectProperties.JvmTarget
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libs.kotlinStdlib)
    implementation(Libs.ktxCore)
    implementation(Libs.appCompat)
    implementation(Libs.material)
    implementation(Libs.constraintlayout)
    testImplementation(Libs.testJunit)
    androidTestImplementation(Libs.androidTestJunit)
    androidTestImplementation(Libs.androidTestespressoCore)
    implementation(Libs.glide)
    implementation(Libs.glideTransformations)
    implementation(Libs.circleimageview)
    implementation(Libs.roomRuntime)
    kapt(Libs.roomCompiler)    // To use Kotlin annotation processing tool (kapt)
    implementation(Libs.roomKtx)    // optional - Kotlin Extensions and Coroutines support for Room
    testImplementation(Libs.roomTesting)    // optional - Test helpers
    implementation(Libs.gson)

}