plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.logggin"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.logggin"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes { release {
        isMinifyEnabled = false
        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
    }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.firestore)
    implementation(libs.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    dependencies {
        implementation("com.squareup.okhttp3:okhttp:4.10.0")
        implementation("com.google.code.gson:gson:2.8.9")
    }
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("org.json:json:20210307")
    implementation("com.google.firebase:firebase-messaging:23.0.0")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("com.google.android.material:material:1.9.0")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
    implementation("androidx.exifinterface:exifinterface:1.3.3")
}
