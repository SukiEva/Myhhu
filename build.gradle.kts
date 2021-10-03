import org.eclipse.jgit.api.Git
import org.eclipse.jgit.internal.storage.file.FileRepository

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
        classpath("org.eclipse.jgit:org.eclipse.jgit:5.12.0.202106070339-r")
    }
}

val repo = FileRepository(rootProject.file(".git"))
val refId = repo.refDatabase.exactRef("refs/remotes/origin/dev-compose").objectId!!
val commitCount = Git(repo).log().add(refId).call().count()


val packageName by extra("com.github.sukieva.hhuer")
val verCode by extra(commitCount + 1000)
val verName by extra("1.0.0")
val targetSdkVersion by extra(30)
val minSdkVersion by extra(27)
val androidBuildToolsVersion by extra("31.0.0")
val compileSdkVersion by extra(31)
val compileNdkVersion by extra("23.0.7599858")


tasks.register("Delete", Delete::class) {
    delete(rootProject.buildDir)
}