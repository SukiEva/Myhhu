plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}


android {
    val targetSdkVersion: Int by rootProject.extra
    val minSdkVersion: Int by rootProject.extra
    val androidBuildToolsVersion: String by rootProject.extra
    val compileSdkVersion: Int by rootProject.extra
    val compileNdkVersion: String by rootProject.extra
    val packageName: String by rootProject.extra
    val verCode: Int by rootProject.extra
    val verName: String by rootProject.extra

    compileSdk = compileSdkVersion
    ndkVersion = compileNdkVersion
    buildToolsVersion = androidBuildToolsVersion

    defaultConfig {
        applicationId = packageName
        minSdk = minSdkVersion
        targetSdk = targetSdkVersion
        versionCode = verCode
        versionName = verName

        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
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
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.2"
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.bundles.androidx)
    implementation(libs.bundles.kotlinx)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.accompanist)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.room)
    implementation(libs.bundles.thirdparty)

    kapt("androidx.room:room-compiler:2.3.0")

    implementation("androidx.appcompat:appcompat:1.3.1")

    debugImplementation("androidx.compose.ui:ui-tooling:1.0.2")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.0.2")

}