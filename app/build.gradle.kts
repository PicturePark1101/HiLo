import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    // hilt 관련
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "ddwucom.moblie.hilo"
    compileSdk = 34

    defaultConfig {
        applicationId = "ddwucom.moblie.hilo"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "KAKAO_API_KEY", getApiKey("KAKAO_API_KEY"))
    }

    buildTypes {
        debug {
            manifestPlaceholders["KAKAO_API_KEY_MANIFEST"] = getApiKey("KAKAO_API_KEY_MANIFEST") as String
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            manifestPlaceholders["KAKAO_API_KEY_MANIFEST"] = getApiKey("KAKAO_API_KEY_MANIFEST") as String
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
// Android 4.0 ~
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    // hilt 관련
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // rxjava 의존성
    implementation("io.reactivex.rxjava3:rxjava:3.0.7")

    // 레트로핏 관련
    implementation("com.squareup.retrofit2:retrofit:2.6.4")
    implementation("com.squareup.retrofit2:converter-gson:2.6.4")
    implementation("com.squareup.retrofit2:converter-scalars:2.6.4")
    implementation("com.squareup.okhttp3:okhttp:3.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:3.11.0")

    // 카카오 로그인
    implementation("com.kakao.sdk:v2-user:2.19.0") // 카카오 로그인

    // 구글 맵
    implementation("com.google.android.gms:play-services-maps:18.2.0")
}

// hilt 관련
kapt {
    correctErrorTypes = true
}

fun getApiKey(propertyKey: String): String {
    val properties = gradleLocalProperties(rootDir)
    return properties.getProperty(propertyKey) ?: throw NoSuchElementException("Property '$propertyKey' not found in local.properties")
//    return gradleLocalProperties(rootDir).getProperty(propertyKey)
}
