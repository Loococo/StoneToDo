plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = AppConfig.applicationId
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = AppConfig.jvmTarget
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = AppConfig.kotlinCompilerExtensionVersion
    }
    packaging {
        resources {
        }
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":presentation"))

    implementation(Version.KTX.CORE)

    implementation(Version.COMPOSE.ACTIVITY)
    implementation(platform(Version.COMPOSE.BOM))
    implementation(Version.COMPOSE.UI)
    implementation(Version.COMPOSE.UI_GRAPHICS)
    implementation(Version.COMPOSE.UI_TOOLING_PREVIEW)
    implementation(Version.COMPOSE.NAVIGATION)

    implementation(Version.AndroidX.MATERIAL3)
    implementation(Version.AndroidX.SPLASH)

    implementation(Version.HILT.HILT_ANDROID)

    kapt(Version.HILT.HILT_ANDROID_COMPILER)


    implementation(Version.RETROFIT.RETROFIT_ANDROID)
    implementation(Version.RETROFIT.RETROFIT_GSON)
    implementation(Version.OKHTTP.OKHTTP3)

    implementation(Version.ROOM.ROOM)
    kapt(Version.ROOM.ROOM_COMPILER)
    implementation(Version.ROOM.ROOM_KTX)
    kapt(Version.ROOM.ROOM_KTX_COMPILER)

    androidTestImplementation(Version.AndroidTest.TEST_RUNNER)
    androidTestImplementation(Version.AndroidTest.ESPRESSO_CORE)
    androidTestImplementation(platform(Version.COMPOSE.BOM))
}