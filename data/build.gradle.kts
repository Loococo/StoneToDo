plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}
android {
    namespace = "app.loococo.data"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        buildToolsVersion = AppConfig.buildToolsVersion
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = AppConfig.jvmTarget
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(Version.HILT.HILT_ANDROID)

    kapt(Version.HILT.HILT_ANDROID_COMPILER)

    implementation(Version.RETROFIT.RETROFIT_ANDROID)
    implementation(Version.RETROFIT.RETROFIT_GSON)

    implementation(Version.ROOM.ROOM)
    kapt(Version.ROOM.ROOM_COMPILER)
    implementation(Version.ROOM.ROOM_KTX)
    kapt(Version.ROOM.ROOM_KTX_COMPILER)
}