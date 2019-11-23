import com.diffplug.gradle.spotless.SpotlessExtension
import io.qameta.allure.gradle.AllureExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.60"
    kotlin("kapt") version "1.3.60"
    id("io.qameta.allure") version "2.8.1"
    id("com.diffplug.gradle.spotless") version "3.26.0"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
}

val micronautVersion = "1.2.6"
val allureVersion = "2.13.0"

dependencyManagement {
    imports {
        mavenBom("io.micronaut:micronaut-bom:$micronautVersion")
    }
}

allprojects {
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "checkstyle")
    apply(plugin = "java-library")
    apply(plugin = "io.qameta.allure")

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
        api("io.micronaut:micronaut-runtime:$micronautVersion")
        api("io.micronaut:micronaut-inject:$micronautVersion")
        api("ch.qos.logback:logback-classic:1.2.3")
        api("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.8")
        kapt("io.micronaut:micronaut-inject-java:$micronautVersion")
        kaptTest("io.micronaut:micronaut-inject-java:$micronautVersion")
        testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
        testImplementation("io.micronaut.test:micronaut-test-junit5:1.1.2")
    }

    configure<AllureExtension> {
        version = allureVersion
        resultsDir = file("$rootDir/build/allure-results")
        useJUnit5 {
            version = allureVersion
        }
    }
}

project(":tests-api") {
    dependencies {
        api("io.micronaut:micronaut-http-client:$micronautVersion")
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
        implementation("io.qameta.allure:allure-selenide:$allureVersion")
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
            .userData(mapOf("disabled_rules" to "import-ordering"))
    }
    kotlinGradle {
        target("**/*gradle.kts")
        ktlint("0.35.0")
    }
}

val wrapper: Wrapper by tasks
wrapper.gradleVersion = "6.0"
