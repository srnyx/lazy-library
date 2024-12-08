import xyz.srnyx.gradlegalaxy.data.pom.DeveloperData
import xyz.srnyx.gradlegalaxy.data.pom.LicenseData
import xyz.srnyx.gradlegalaxy.enums.Repository
import xyz.srnyx.gradlegalaxy.enums.repository
import xyz.srnyx.gradlegalaxy.utility.setupJda
import xyz.srnyx.gradlegalaxy.utility.setupPublishing


plugins {
    application
    id("xyz.srnyx.gradle-galaxy") version "1.3.2"
    id("com.gradleup.shadow") version "8.3.5"
}

setupJda("5.1.2", "xyz.srnyx", "3.2.0", "A simple library for JDA Discord bots", JavaVersion.VERSION_21)

repository(Repository.JITPACK)
dependencies {
    implementation("xyz.srnyx", "java-utilities", "2.0.0") // General Java utility library
    implementation("io.github.freya022", "BotCommands", "2.10.4") // Command framework
    implementation("org.spongepowered", "configurate-yaml", "4.1.2") // Config manager
    implementation("org.postgresql", "postgresql", "42.7.4") // Database
    implementation("com.zaxxer", "HikariCP", "6.0.0") // Database
    implementation("ch.qos.logback", "logback-classic", "1.5.8") // Logging
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
