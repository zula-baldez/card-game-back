import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.1-SNAPSHOT"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.spring") version "1.8.21"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:3.1.0")
    implementation("org.springframework.boot:spring-boot-configuration-processor:3.1.0")
    implementation("org.springframework.boot:spring-boot-starter-data-rest:3.0.4")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.0.4")
    implementation("org.springframework.security:spring-security-core:6.1.0")
    implementation("org.springframework.security.oauth:spring-security-oauth2:2.5.2.RELEASE")
    implementation("org.springframework.security:spring-security-oauth2-jose:6.1.0")
    implementation("org.springframework.security:spring-security-oauth2-client:6.1.1")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")

    implementation("org.springframework.security:spring-security-config:6.1.0")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")
    implementation("org.liquibase:liquibase-core:4.22.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.google.code.gson:gson:2.10.1")
    compileOnly("org.projectlombok:lombok:1.18.26")
    developmentOnly("org.springframework.boot:spring-boot-devtools:3.0.4")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.0")
    implementation("org.postgresql:postgresql:42.2.27")

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
