import xyz.srnyx.gradlegalaxy.data.pom.DeveloperData
import xyz.srnyx.gradlegalaxy.data.pom.LicenseData
import xyz.srnyx.gradlegalaxy.enums.Repository
import xyz.srnyx.gradlegalaxy.enums.repository
import xyz.srnyx.gradlegalaxy.utility.addCompilerArgs
import xyz.srnyx.gradlegalaxy.utility.setupJava
import xyz.srnyx.gradlegalaxy.utility.setupPublishing


plugins {
    application
    id("xyz.srnyx.gradle-galaxy") version "1.1.2"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

setupJava("xyz.srnyx", "3.0.1", "A simple library for JDA Discord bots", JavaVersion.VERSION_19)
application.mainClass.set("xyz.srnyx.lazylibrary.LazyLibrary")
addCompilerArgs("-parameters")

repository(Repository.MAVEN_CENTRAL, Repository.JITPACK)
dependencies {
    compileOnly("net.dv8tion", "JDA", "5.0.0-beta.21") // JDA
    implementation("xyz.srnyx", "java-utilities", "1.0.0") // General Java utility library
    implementation("xyz.srnyx", "magic-mongo", "1.2.1") // MongoDB framework
    implementation("io.github.freya022", "BotCommands", "2.10.3") // Command framework
    implementation("org.spongepowered", "configurate-yaml", "4.1.2") // Config manager
    implementation("org.postgresql", "postgresql", "42.7.3") // Database
    implementation("com.zaxxer", "HikariCP", "5.1.0") // Database
    implementation("ch.qos.logback", "logback-classic", "1.5.3") // Logging
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
