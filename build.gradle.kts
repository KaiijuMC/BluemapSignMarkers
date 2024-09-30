import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21

plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "8.0.0"
    id("xyz.jpenilla.run-paper") version "2.0.1"
    id("idea")
    kotlin("jvm")
}


group = "dev.kugge"
version = "0.0.3"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.bluecolored.de/releases")
    maven("https://jitpack.io")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly("dev.folia:folia-api:1.19.4-R0.1-SNAPSHOT")
    compileOnly ("de.bluecolored.bluemap:BlueMapAPI:2.7.2")
    implementation("net.pwall.mustache:kotlin-mustache:0.12")
    implementation(kotlin("stdlib"))
    testImplementation(platform("org.junit:junit-bom:5.11.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.mockito:mockito-core:3.+")
    testImplementation("net.kyori:adventure-api:4.13.0")
    testImplementation("dev.folia:folia-api:1.19.4-R0.1-SNAPSHOT")
    testImplementation ("de.bluecolored.bluemap:BlueMapAPI:2.7.2")
}

tasks.compileJava {
    options.encoding = Charsets.UTF_8.name()
    options.release.set(21)
}

tasks.processResources {
    filter { line -> line.replace("\${version}", project.version.toString()) }
}

tasks.shadowJar {
    archiveFileName.set("SignMarkers-${project.version}.jar")
    mergeServiceFiles()
}

tasks.jar {
    enabled = false
}

tasks.assemble {
    dependsOn(tasks.shadowJar)
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
