import xyz.srnyx.gradlegalaxy.data.pom.DeveloperData
import xyz.srnyx.gradlegalaxy.data.pom.LicenseData
import xyz.srnyx.gradlegalaxy.enums.Repository
import xyz.srnyx.gradlegalaxy.enums.repository
import xyz.srnyx.gradlegalaxy.utility.setupJda
import xyz.srnyx.gradlegalaxy.utility.setupPublishing


plugins {
    application
    id("xyz.srnyx.gradle-galaxy") version "1.3.3"
    id("com.gradleup.shadow") version "8.3.6"
}

setupJda("5.2.2", "xyz.srnyx", "3.2.0", "A simple library for JDA Discord bots", JavaVersion.VERSION_21)

repository(Repository.JITPACK)
dependencies {
    implementation("xyz.srnyx", "java-utilities", "b83b598cc2") // General Java utility library
    implementation("io.github.freya022", "BotCommands", "2.10.4") // Command framework
    implementation("org.spongepowered", "configurate-yaml", "4.1.2") // Config manager
    implementation("org.postgresql", "postgresql", "42.7.5") // Database
    implementation("com.zaxxer", "HikariCP", "6.2.1") // Database
    implementation("ch.qos.logback", "logback-classic", "1.5.16") // Logging
    implementation("dev.freya02", "jda-emojis", "3.0.0") // Emojis
}

setupPublishing(
    artifactId = "lazy-library",
    url = "https://lazy-library.srnyx.com",
    licenses = listOf(LicenseData.MIT),
    developers = listOf(DeveloperData.srnyx))

// Fix some tasks
tasks {
    startScripts {
        dependsOn("shadowJar")
    }
    startShadowScripts {
        dependsOn("jar")
    }
}
