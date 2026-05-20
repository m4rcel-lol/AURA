plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.secrets)
}

android {
    namespace = "dev.m5rcel.aura"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.m5rcel.aura"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
        debug {
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += listOf(
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
        )
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

secrets {
    propertiesFileName = ".env"
    defaultPropertiesFileName = ".env.example"
}

dependencies {
    implementation(libs.androidx.core-ktx)
    implementation(libs.androidx.lifecycle-runtime-ktx)
    implementation(libs.androidx.activity-compose)
    
    // Compose
    implementation(platform(libs.androidx.compose-bom))
    implementation(libs.androidx.compose-ui)
    implementation(libs.androidx.compose-ui-graphics)
    implementation(libs.androidx.compose-ui-tooling-preview)
    implementation(libs.androidx.compose-material3)
    implementation(libs.androidx.compose-material-icons-extended)
    debugImplementation(libs.androidx.compose-ui-tooling)

    // Room
    implementation(libs.androidx.room-runtime)
    implementation(libs.androidx.room-ktx)
    ksp(libs.androidx.room-compiler)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation-compose)
    implementation(libs.androidx.hilt.work)

    // Network & Retrofit
    implementation(libs.retrofit2.retrofit)
    implementation(libs.retrofit2.converter-gson)
    implementation(libs.okhttp3.okhttp)
    implementation(libs.okhttp3.logging-interceptor)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network)

    // Permissions (Accompanist)
    implementation(libs.accompanist.permissions)

    // DataStore
    implementation(libs.androidx.datastore-preferences)

    // WorkManager
    implementation(libs.androidx.work.runtime-ktx)

    // Play Services Location
    implementation(libs.play.services-location)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso-core)
    androidTestImplementation(platform(libs.androidx.compose-bom))
}
