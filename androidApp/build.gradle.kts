plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

// A thin Android shell: manifest, launcher resources and the Activity. All the
// catalog UI lives in :composeApp, which is shared with desktop, iOS and web.
// AGP 9 refuses to apply `com.android.application` alongside the Kotlin
// Multiplatform plugin, so the shell has to be its own Android-only module.
android {
    namespace = "com.example.sketchy"
    compileSdk {
        version = release(37) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.sketchy"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":composeApp"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
}
