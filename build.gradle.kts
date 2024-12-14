plugins {
    kotlin("jvm") version "2.1.0"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
    testImplementation("junit:junit:4.13.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("org.jgrapht:jgrapht-core:1.5.2")
}