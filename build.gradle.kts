// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
    }
}

val defaultManagerPackageName by extra("github.sukieva.hhuer")
val verCode by extra(20210924)
val verName by extra("1.0")
val androidTargetSdkVersion by extra(31)
val androidMinSdkVersion by extra(27)
val androidBuildToolsVersion by extra("31.0.0")
val androidCompileSdkVersion by extra(31)
val androidCompileNdkVersion by extra("23.0.7599858")
val androidSourceCompatibility by extra(JavaVersion.VERSION_1_8)
val androidTargetCompatibility by extra(JavaVersion.VERSION_1_8)


tasks.register("Delete", Delete::class) {
    delete(rootProject.buildDir)
}