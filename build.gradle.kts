buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.49")
    }
}
plugins {
    id("com.android.application") version AppConfig.pluginVersion apply false
    id("com.android.library") version AppConfig.pluginVersion apply false
    id("org.jetbrains.kotlin.android") version AppConfig.kotlin apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.0" apply false
}