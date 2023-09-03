import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*

plugins {
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
    id ("org.jetbrains.kotlin.plugin.lombok") version "1.8.0"
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.spring") version "1.8.21"
    id ("org.jetbrains.kotlin.plugin.jpa") version "1.5.21"
    id ("org.jetbrains.kotlin.plugin.allopen") version "1.7.10"

    id("com.google.protobuf") version "0.8.19"


}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.tomcat:tomcat-annotations-api:11.0.0-M9")
    implementation("com.google.protobuf:protobuf-kotlin:3.24.0-RC2")
    implementation("com.google.protobuf:protobuf-kotlin:3.19.4")
    implementation("io.grpc:protoc-gen-grpc-java:1.57.0")
    implementation("net.devh:grpc-server-spring-boot-starter:2.12.0.RELEASE")

    api("io.grpc:grpc-protobuf:1.44.0")
    api("com.google.protobuf:protobuf-java-util:3.19.4")
    api("com.google.protobuf:protobuf-kotlin:3.19.4")
    api("io.grpc:grpc-kotlin-stub:1.2.1")
    api("io.grpc:grpc-stub:1.44.0")
    runtimeOnly("io.grpc:grpc-netty:1.44.0")
    sourceSets {
        main {
            proto {
                srcDir("src/main/protobuf")
            }
        }
    }




    implementation("org.springframework.boot:spring-boot-starter-security:3.1.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
    implementation("org.springframework.security:spring-security-oauth2-jose:6.1.0")

    implementation("org.springframework.security:spring-security-config:6.1.0")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")


    implementation("org.springframework.security:spring-security-messaging:6.1.1")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework:spring-websocket:6.0.10")
    implementation("org.springframework:spring-messaging:6.0.10")
    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.1.1")

    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}
protobuf {
    protoc {
        // The artifact spec for the Protobuf Compiler
        artifact = "com.google.protobuf:protoc:3.6.1"
    }
    plugins {
        // Optional: an artifact spec for a protoc plugin, with "grpc" as
        // the identifier, which can be referred to in the "plugins"
        // container of the "generateProtoTasks" closure.
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.15.1"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                // Apply the "grpc" plugin whose spec is defined above, without
                // options. Note the braces cannot be omitted, otherwise the
                // plugin will not be added. This is because of the implicit way
                // NamedDomainObjectContainer binds the methods.
                id("grpc") { }
            }
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
