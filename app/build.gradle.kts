plugins {
    id("com.android.application")
    id("kotlin-android")
}



android {
    val androidTargetSdkVersion: Int by rootProject.extra
    val androidMinSdkVersion: Int by rootProject.extra
    val androidBuildToolsVersion: String by rootProject.extra
    val androidCompileSdkVersion: Int by rootProject.extra
    val androidCompileNdkVersion: String by rootProject.extra
    val defaultManagerPackageName: String by rootProject.extra
    val verCode: Int by rootProject.extra
    val verName: String by rootProject.extra


    compileSdk = androidCompileSdkVersion
    ndkVersion = androidCompileNdkVersion
    buildToolsVersion = androidBuildToolsVersion

    defaultConfig {
        applicationId = defaultManagerPackageName
        minSdk = androidMinSdkVersion
        targetSdk = androidTargetSdkVersion
        versionCode = verCode
        versionName = verName

        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles("proguard-rules.pro")
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
    implementation(libs.bundles.thirdparty)

}