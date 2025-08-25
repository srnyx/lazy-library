import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.kotlin.dsl.withType
import xyz.srnyx.gradlegalaxy.data.pom.DeveloperData
import xyz.srnyx.gradlegalaxy.data.pom.LicenseData
import xyz.srnyx.gradlegalaxy.enums.Repository
import xyz.srnyx.gradlegalaxy.enums.repository
import xyz.srnyx.gradlegalaxy.utility.setupJda
import xyz.srnyx.gradlegalaxy.utility.setupPublishing


plugins {
    application
    id("xyz.srnyx.gradle-galaxy") version "1.3.3"
    id("com.gradleup.shadow") version "8.3.8"
    id("dev.reformator.stacktracedecoroutinator") version "2.5.6"
}

setupJda("5.6.1", "xyz.srnyx", "3.2.0", "A simple library for JDA Discord bots", JavaVersion.VERSION_22)

repository(Repository.JITPACK)
dependencies {
    implementation("xyz.srnyx", "java-utilities", "a97f2fedd5") // General Java utility library
    implementation("com.google.code.gson", "gson", "2.3.1") // Use this specific version for Java Utilities
    implementation("io.github.freya022", "BotCommands", "3.0.0-beta.3") // Command framework
    implementation("org.spongepowered", "configurate-yaml", "4.2.0") // Config manager
    implementation("org.flywaydb", "flyway-database-postgresql", "11.10.3") // Database
    implementation("org.postgresql", "postgresql", "42.7.7") // Database
    implementation("com.zaxxer", "HikariCP", "7.0.0") // Database
    implementation("ch.qos.logback", "logback-classic", "1.5.18") // Logging
    implementation("dev.freya02", "jda-emojis", "3.0.0") // Emojis
    implementation("org.jetbrains.kotlin", "kotlin-stdlib", "2.2.10") // Kotlin (for utilities)
}

// Fix Java's service loading, which Flyway uses
tasks.withType<ShadowJar> { mergeServiceFiles() }

setupPublishing(
    artifactId = "lazy-library",
    url = "https://lazy-library.srnyx.com",
    licenses = listOf(LicenseData.MIT),
    developers = listOf(DeveloperData.srnyx))

// Fix some tasks
tasks {
    startScripts { dependsOn("shadowJar") }
    startShadowScripts { dependsOn("jar") }
}
