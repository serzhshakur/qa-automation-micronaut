import com.diffplug.gradle.spotless.SpotlessExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.50"
    kotlin("kapt") version "1.3.50"
    id("io.qameta.allure") version "2.8.1"
    id("com.diffplug.gradle.spotless") version "3.25.0"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
}

dependencyManagement {
    imports {
        mavenBom("io.micronaut:micronaut-bom:1.2.6")
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
        api("io.micronaut:micronaut-runtime:1.2.6")
        api("io.micronaut:micronaut-inject:1.2.6")
        api("ch.qos.logback:logback-classic:1.2.3")
        api("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.8")
        kapt("io.micronaut:micronaut-inject-java:1.2.6")
        kaptTest("io.micronaut:micronaut-inject-java:1.2.6")
        testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
        testImplementation("io.micronaut.test:micronaut-test-junit5:1.1.2")
    }

}

project(":tests-api") {
    dependencies {
        api("io.micronaut:micronaut-http-client:1.2.6")
        testImplementation("org.assertj:assertj-core:3.11.1")
    }

    task<Test>("testApi") {
        systemProperties(
            "api.openweather.token" to System.getProperty("apiToken")
        )
        useJUnitPlatform {
            filter {
                includeTestsMatching("dev.serzhshakur.api.*")
            }
        }
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}

project(":tests-web") {
    dependencies {
        implementation("com.codeborne:selenide:5.5.0")
    }

    task<Test>("testWeb") {
        systemProperties(
            "selenide.headless" to (System.getProperty("headless") ?: "false"),
            "selenide.browser" to (System.getProperty("browser") ?: "chrome")
        )

        useJUnitPlatform {
            filter {
                includeTestsMatching("dev.serzhshakur.web.*")
            }
        }
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
        ktlint("0.35.0")
    }
    kotlinGradle {
        target("**/*gradle.kts")
        ktlint("0.35.0")
    }
}

val wrapper: Wrapper by tasks
wrapper.gradleVersion = "5.6"
