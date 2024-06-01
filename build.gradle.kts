import xyz.srnyx.gradlegalaxy.data.pom.DeveloperData
import xyz.srnyx.gradlegalaxy.data.pom.LicenseData
import xyz.srnyx.gradlegalaxy.enums.Repository
import xyz.srnyx.gradlegalaxy.enums.repository
import xyz.srnyx.gradlegalaxy.utility.addCompilerArgs
import xyz.srnyx.gradlegalaxy.utility.setupJava
import xyz.srnyx.gradlegalaxy.utility.setupPublishing


plugins {
    application
    id("xyz.srnyx.gradle-galaxy") version "1.1.3"
    id("io.github.goooler.shadow") version "8.1.7"
}

setupJava("xyz.srnyx", "3.1.0", "A simple library for JDA Discord bots", JavaVersion.VERSION_19)
application.mainClass.set("xyz.srnyx.lazylibrary.LazyLibrary")
addCompilerArgs("-parameters")

repository(Repository.MAVEN_CENTRAL, Repository.JITPACK)
dependencies {
    compileOnly("net.dv8tion", "JDA", "5.0.0-beta.24") // JDA
    implementation("xyz.srnyx", "java-utilities", "a71551bc2d") // General Java utility library
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
