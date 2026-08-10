import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.0.21"
}


android {
    namespace = "com.cranoxz.streamroom"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.cranoxz.streamroom"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    val properties = Properties().apply {
        val localProperties = project.rootProject.file("local.properties")
        if (localProperties.exists()) {
            load(FileInputStream(localProperties))
        }
    }

    @Suppress("LocalVariableName")
    val LOCAL = properties.getProperty("LOCAL") ?: ""
    @Suppress("LocalVariableName")
    val PROD = properties.getProperty("PROD") ?: ""

    buildTypes {
        debug {
            isMinifyEnabled = false
            buildConfigField("String", "BASE_URL", "\"$LOCAL\"")
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            optimization {
                enable = false
            }
            buildConfigField("String", "BASE_URL", "\"$PROD\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}
val ktor: String by project
val media3: String by project
val logback: String by project
dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    // Jetpack Media3 (ExoPlayer + HLS Support)
    implementation("androidx.media3:media3-exoplayer:$media3")
    implementation("androidx.media3:media3-exoplayer-hls:$media3")
    implementation("androidx.media3:media3-ui:$media3")

    // Ktor Client for WebSockets & HTTP Uploads
    implementation("io.ktor:ktor-client-core:${ktor}")
    implementation("io.ktor:ktor-client-android:${ktor}")
    implementation("io.ktor:ktor-client-okhttp:$ktor")
    implementation("io.ktor:ktor-client-websockets:$ktor")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor")
    implementation("io.ktor:ktor-serialization-gson:$ktor")
    implementation("io.ktor:ktor-client-logging:$ktor")
    //viewmodel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.10.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0")
    //navigations
    implementation(libs.androidx.compose.navigation)
    //
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

}