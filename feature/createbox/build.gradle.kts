plugins {
    id("android.library.feature")
}

android {
    namespace = "com.packy.createbox"
}

dependencies {
    implementation(project(":di"))
    implementation(project(":domain"))
    implementation(project(":library:mvi"))
    implementation(project(":library:account"))
    implementation(project(":feature:core"))
    implementation(project(":common-android"))
    implementation(project(":lib"))

    implementation(libs.accompanist)
    implementation(libs.bundles.paging)
    implementation(libs.permissions)
    implementation(libs.lottie)
}