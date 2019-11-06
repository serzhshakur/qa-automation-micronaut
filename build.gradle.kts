import com.diffplug.gradle.spotless.SpotlessExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.50"
    kotlin("kapt") version "1.3.50"
    id("io.qameta.allure") version "2.8.1"
    id("com.diffplug.gradle.spotless") version "3.23.1"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
}

dependencyManagement {
    imports {
        mavenBom("io.micronaut:micronaut-bom:1.1.4")
    }
}

allprojects {
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "checkstyle")
    apply(plugin = "java-library")

    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    val compileKotlin: KotlinCompile by tasks
    val compileTestKotlin: KotlinCompile by tasks

    compileKotlin.kotlinOptions.jvmTarget = "11"
    compileTestKotlin.kotlinOptions.jvmTarget = "11"

    repositories {
        jcenter()
        mavenCentral()
    }

    dependencies {
        api("io.micronaut:micronaut-runtime:1.1.4")
        api("io.micronaut:micronaut-http-client:1.1.4")
        api("io.micronaut:micronaut-inject:1.1.4")
        api("ch.qos.logback:logback-classic:1.2.3")
        api("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.8")
        kapt("io.micronaut:micronaut-inject-java:1.1.4")
        kaptTest("io.micronaut:micronaut-inject-java:1.1.4")
        testImplementation("org.junit.jupiter:junit-jupiter:5.4.2")
        testImplementation("io.micronaut.test:micronaut-test-junit5:1.1.1")
        testImplementation("io.micronaut.test:micronaut-test-junit5:1.1.1")
        testImplementation("org.assertj:assertj-core:3.11.1")
    }

    tasks.test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}

configure<SpotlessExtension> {
    format("misc") {
        target("*/src/**/*.kt")
        trimTrailingWhitespace()
    }
    kotlin {
        target("*/src/**/*.kt")
        ktlint("0.33.0")
    }
    kotlinGradle {
        target("**/*gradle.kts")
        ktlint("0.33.0")
    }
}

val wrapper: Wrapper by tasks
wrapper.gradleVersion = "5.6"
