plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
    alias(libs.plugins.compose.compiler)

}
/*
* Keep compileSdkVersion updated: To ensure you can use the latest APIs and features, update compileSdkVersion to the latest SDK version regularly.
Update targetSdkVersion carefully: Set targetSdkVersion to the latest version you have tested your app against. This ensures your app can benefit from the latest platform optimizations and security features while maintaining backward compatibility.
* */

android {
    namespace = "com.ashmit.googlecredentialmanager"
    compileSdk = 35 //changed this to 35 from 34 , pecifies the version of the Android SDK that Gradle uses to compile your app
    compileSdkPreview = "VanillaIceCream"//added this because the android sdk 35 contains biometrics whihc require vanillaIceCream for the sdk preview

    defaultConfig {
        applicationId = "com.ashmit.googlecredentialmanager"
        minSdk = 24
        targetSdk = 34 // this is the target sdk Indicates the API level that your app is targeting and has been tested with.
        versionCode = 1
        versionName = "1.0"

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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.credentials:credentials:1.5.0-alpha03")
    implementation("androidx.credentials:credentials-play-services-auth:1.5.0-alpha03")
    implementation ("com.google.android.libraries.identity.googleid:googleid:1.1.1")
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-analytics")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("io.coil-kt:coil-compose:2.2.2")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}