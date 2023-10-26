import com.google.protobuf.gradle.id
import java.net.URI

plugins {
    kotlin("jvm") version "1.9.0"
    id("com.google.protobuf") version "0.9.2"
    id("java-library")
    id("maven-publish")
}

group = "wedding.kanshasai"
version = "1.4.0"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(17)
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.clean {
    delete("build/generated")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.23.4"
    }
    plugins {
        id("grpc") { artifact = "io.grpc:protoc-gen-grpc-java:1.57.0" }
        id("grpckt") { artifact = "io.grpc:protoc-gen-grpc-kotlin:1.3.0:jdk8@jar" }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach { task ->
            task.plugins {
                id("grpc") { outputSubDir = "java" }
                id("grpckt") { outputSubDir = "java" }
            }
            task.dependsOn("clean")
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("release") {
            from(components["java"])
        }
        repositories {
            maven {
                name = "mizucoffeeRepository"
                url = URI("https://nexus.mizucoffee.net/repository/maven-releases/")
                credentials(PasswordCredentials::class)
            }
        }
    }
}

tasks.register("printVersion") {
    doLast {
        println(project.version)
    }
}

dependencies {
    api("com.google.protobuf:protobuf-kotlin:3.23.4")
    api("io.grpc:grpc-kotlin-stub:1.3.0")
    api("io.grpc:grpc-protobuf:1.57.0")
    api("javax.annotation:javax.annotation-api:1.3.2")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}
