plugins {
    id("com.android.application")
}

android {
    namespace = "moe.zapic.bilisetfull"
    compileSdk = 33

    defaultConfig {
        applicationId = "moe.zapic.bilisetfull"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}


dependencies {
    compileOnly("de.robv.android.xposed:api:82")
}