@file:Suppress("UnstableApiUsage")

enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}
rootProject.name = "hhu"
include(":app")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            val composeVersion = "1.0.2"
            val accompanistVersion = "0.18.0"
            val lifecycleVersion = "2.4.0-beta01"
            val retrofitVersion = "2.9.0"
            val roomVersion = "2.3.0"


            // AndroidX
            alias("androidx-core").to("androidx.core:core-ktx:1.6.0")
            alias("androidx-activityCompose").to("androidx.activity:activity-compose:1.3.1")
            alias("androidx-datastorePreferences").to("androidx.datastore:datastore-preferences:1.0.0")
            alias("google-material").to("com.google.android.material:material:1.4.0")
            bundle(
                "androidx", listOf(
                    "androidx-core",
                    "androidx-activityCompose",
                    "google-material",
                    "androidx-datastorePreferences" // DataStore Preferences
                )
            )

            // Kotlin
            alias("kotlinx-coroutines").to("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0")
            bundle(
                "kotlinx", listOf(
                    "kotlinx-coroutines"
                )
            )

            // Compose
            alias("compose-ui").to("androidx.compose.ui:ui:$composeVersion")
            alias("compose-uiToolingPreview").to("androidx.compose.ui:ui-tooling-preview:$composeVersion")
            alias("compose-foundation").to("androidx.compose.foundation:foundation:$composeVersion")
            alias("compose-material").to("androidx.compose.material:material:$composeVersion")
            alias("compose-materialIcons").to("androidx.compose.material:material-icons-core:$composeVersion")
            alias("compose-materialIconsExtended").to("androidx.compose.material:material-icons-extended:$composeVersion")
            alias("compose-uiTestJunit").to("androidx.compose.ui:ui-test-junit4:$composeVersion")
            bundle(
                "compose", listOf(
                    "compose-ui",
                    "compose-uiToolingPreview", // Tooling support (Previews, etc.)
                    "compose-foundation", // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
                    "compose-material", // Material Design
                    "compose-materialIcons", // Material design icons
                    "compose-materialIconsExtended"
                )
            )

            // Accompanist
            alias("accompanist-insets").to("com.google.accompanist:accompanist-insets:$accompanistVersion")
            alias("accompanist-insetsUi").to("com.google.accompanist:accompanist-insets-ui:$accompanistVersion")
            alias("accompanist-systemuicontroller").to("com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion")
            bundle(
                "accompanist", listOf(
                    "accompanist-insets",
                    "accompanist-insetsUi",
                    "accompanist-systemuicontroller"
                )
            )

            // Lifecycle
            alias("lifecycle-viewmodelCompose").to("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
            alias("lifecycle-runtimeKtx").to("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
            alias("lifecycle-livedataKtx").to("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
            bundle(
                "lifecycle", listOf(
                    "lifecycle-viewmodelCompose", // viewModel
                    "lifecycle-runtimeKtx", // Lifecycles only (without ViewModel or LiveData)
                    "lifecycle-livedataKtx", // liveData
                )
            )

            // Retrofit
            alias("squareup-retrofit").to("com.squareup.retrofit2:retrofit:$retrofitVersion")
            alias("squareup-gson").to("com.squareup.retrofit2:converter-gson:$retrofitVersion")
            bundle(
                "retrofit", listOf(
                    "squareup-retrofit",
                    "squareup-gson"
                )
            )

            // Room
            alias("room-runtime").to("androidx.room:room-runtime:$roomVersion")
            alias("room-ktx").to("androidx.room:room-ktx:$roomVersion")
            bundle(
                "room", listOf(
                    "room-runtime",
                    "room-ktx"
                )
            )

            // Third Party
            alias("drakeet-about").to("com.drakeet.about:about:2.4.1")
            alias("drakeet-multitype").to("com.drakeet.multitype:multitype:4.3.0")
            alias("github-toasty").to("com.github.GrenderG:Toasty:1.5.2")
            alias("coil-compose").to("io.coil-kt:coil-compose:1.3.2")
            bundle(
                "thirdparty", listOf(
                    "drakeet-about",
                    "drakeet-multitype",
                    "github-toasty",
                    "coil-compose" // 网络加载远程图片
                )
            )
        }
    }
}