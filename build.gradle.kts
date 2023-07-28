plugins {
    kotlin("jvm") version "1.9.0"
    id("build.buf") version "0.8.4"
}

group = "wedding.kanshasai-1105"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}