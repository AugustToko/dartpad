import java.io.File
import java.io.FileInputStream
import java.util.*

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.6.20"
    id("org.jetbrains.intellij") version "1.5.2"
}

group = "top.geekcloud"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
    version.set("2021.2")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf(/* Plugin Dependencies */))
}

val prop = Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, "local.properties")))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    patchPluginXml {
        sinceBuild.set("212")
        untilBuild.set("222.*")
    }

    @Suppress("LocalVariableName") val CERTIFICATE_CHAIN: String = File("chain.crt").readText()
    @Suppress("LocalVariableName") val PRIVATE_KEY: String = File("private.pem").readText()

    signPlugin {
        certificateChain.set(CERTIFICATE_CHAIN)
        privateKey.set(PRIVATE_KEY)
        password.set(prop["private_pem_password"].toString())
    }

    publishPlugin {
        token.set(prop["ORG_GRADLE_PROJECT_intellijPublishToken"].toString())
        channels.set(listOf("beta"))
    }
}

val ktorVersion: String by project

dependencies {
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
}