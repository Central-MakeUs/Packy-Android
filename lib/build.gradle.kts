plugins {
    id("java-library")
    id("kotlin.library")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies{
    implementation(libs.kotlin.stdlib)
    implementation(libs.bundles.ktor)
}