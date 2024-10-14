plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "ru.vafeen.universityschedule"
    compileSdk = 35

    defaultConfig {
        applicationId = "ru.vafeen.universityschedule"
        minSdk = 26
        targetSdk = 35
        versionCode = 25
        versionName = "3.9"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
//    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // room
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    //retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    // compose navigation
    implementation(libs.androidx.navigation.compose)
    // viewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    // color-picker
    implementation(libs.alwan)
    //permission
    implementation(libs.accompanist.permissions)
    //koin
    implementation(libs.koin.android)
    // koin for compose
    implementation(libs.koin.androidx.compose)
}