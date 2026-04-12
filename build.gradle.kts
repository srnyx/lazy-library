import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.kotlin.dsl.withType
import xyz.srnyx.gradlegalaxy.data.config.DependencyConfig
import xyz.srnyx.gradlegalaxy.data.config.JavaSetupConfig
import xyz.srnyx.gradlegalaxy.data.config.publishingSimpleConfig
import xyz.srnyx.gradlegalaxy.data.pom.DeveloperData
import xyz.srnyx.gradlegalaxy.data.pom.LicenseData
import xyz.srnyx.gradlegalaxy.enums.Repository
import xyz.srnyx.gradlegalaxy.enums.repository
import xyz.srnyx.gradlegalaxy.utility.setupJda
import xyz.srnyx.gradlegalaxy.utility.setupPublishingEnv


plugins {
    application
    `java-library`
    id("xyz.srnyx.gradle-galaxy") version "2.1.0"
    id("com.gradleup.shadow") version "8.3.9"
    id("dev.reformator.stacktracedecoroutinator") version "2.6.2"
}

setupJda(
    javaSetupConfig = JavaSetupConfig(
        group = "xyz.srnyx",
        version = "4.0.0",
        description = "A simple library for JDA Discord bots"),
    jdaConfig = DependencyConfig(version = "6.4.1"))

repository(Repository.SRNYX_RELEASES, Repository.SRNYX_SNAPSHOTS)
dependencies {
    api("xyz.srnyx:java-utilities:4a0f974") // General Java utility library
    api("com.google.code.gson:gson:2.3.1") // Use this specific version for Java Utilities
    api("io.github.freya022:BotCommands:3.1.0") // Command framework
    api("org.spongepowered:configurate-yaml:4.2.0") // Config manager
    api("dev.freya02:jda-emojis:4.1.0") // Emojis
    implementation("ch.qos.logback:logback-classic:1.5.32") // Logging
    implementation("com.zaxxer:HikariCP:7.0.2") // Database
    implementation("org.flywaydb:flyway-core:12.3.0") // Database
    runtimeOnly("org.flywaydb:flyway-database-postgresql:12.3.0") // Database
    runtimeOnly("org.postgresql:postgresql:42.7.10") // Database
}

// Fix Java's service loading, which Flyway uses
tasks.withType<ShadowJar> { mergeServiceFiles() }

// Show warnings for missing Javadocs
tasks.withType<Javadoc> { (options as StandardJavadocDocletOptions).addStringOption("Xdoclint:all", "-quiet") }

setupPublishingEnv(publishingSimpleConfig(
    artifactId = "lazy-library",
    url = "https://lazy-library.srnyx.com",
    licenses = listOf(LicenseData.MIT),
    developers = listOf(DeveloperData.srnyx)))
