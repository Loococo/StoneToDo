object Version {
    object AndroidX {
        const val MATERIAL3 = "androidx.compose.material3:material3"
        const val SPLASH = "androidx.core:core-splashscreen:1.2.0-alpha01"
    }

    object KTX {
        const val CORE = "androidx.core:core-ktx:1.12.0"
    }

    object COMPOSE {
        const val ACTIVITY = "androidx.activity:activity-compose:1.9.0-alpha03"
        const val BOM = "androidx.compose:compose-bom:2024.02.00"
        const val UI = "androidx.compose.ui:ui:1.6.2"
        const val UI_GRAPHICS = "androidx.compose.ui:ui-graphics"
        const val UI_TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview"
        const val NAVIGATION = "androidx.navigation:navigation-compose:2.7.6"
        const val LIVEDATA = "androidx.compose.runtime:runtime-livedata"
        const val LIFECYCLE = "androidx.lifecycle:lifecycle-runtime-compose:2.7.0"
    }

    object AndroidTest {
        const val TEST_RUNNER = "androidx.test.ext:junit:1.1.5"
        const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:3.5.1"
    }

    object HILT {
        const val HILT_ANDROID = "com.google.dagger:hilt-android:2.49"
        const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:2.49"
        const val HILT_VIEWMODEL = "androidx.hilt:hilt-navigation-compose:1.2.0"
    }

    object RETROFIT {
        const val RETROFIT_ANDROID = "com.squareup.retrofit2:retrofit:2.9.0"
        const val RETROFIT_GSON = "com.squareup.retrofit2:converter-gson:2.9.0"
    }

    object OKHTTP {
        const val OKHTTP3 = "com.squareup.okhttp3:logging-interceptor:4.12.0"
    }

    object ROOM {
        const val ROOM = "androidx.room:room-runtime:2.6.1"
        const val ROOM_COMPILER = "androidx.room:room-compiler:2.6.1"
        const val ROOM_KTX = "androidx.room:room-ktx:2.6.1"
        const val ROOM_KTX_COMPILER = "android.arch.persistence.room:compiler:1.1.1"
    }
}