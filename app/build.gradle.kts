plugins {
    id("android.application.core")
}

android {
    namespace = "com.packy"

    defaultConfig{
        versionCode = 1
        versionName = "1.0.0"
        applicationId = "packyforyou.shop"
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes{
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(project(":di"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":feature:main"))

    implementation(project(":library:pref"))
    implementation(project(":library:account"))
    implementation(libs.kakao.user)
}