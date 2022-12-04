plugins {
    kotlin("jvm") version "1.7.21"
    id("org.jmailen.kotlinter") version "3.12.0"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")
    testImplementation("junit:junit:4.13.2")
}