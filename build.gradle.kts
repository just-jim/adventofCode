plugins {
    kotlin("jvm") version "1.9.21"
    id("org.jmailen.kotlinter") version "4.1.0"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
    testImplementation("junit:junit:4.13.2")
}
