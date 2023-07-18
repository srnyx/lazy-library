import xyz.srnyx.gradlegalaxy.data.pom.DeveloperData
import xyz.srnyx.gradlegalaxy.data.pom.LicenseData
import xyz.srnyx.gradlegalaxy.utility.setupPublishing


plugins {
    `java-library`
}

application.mainClass.set("xyz.srnyx.lazylibrary.LazyLibrary")

dependencies {
    api("io.github.freya022", "BotCommands", "2.10.2") // Command framework
    api("org.spongepowered", "configurate-yaml", "4.1.2") // Data storage
    api("org.postgresql", "postgresql", "42.6.0") // Database
    api("com.zaxxer", "HikariCP", "5.0.1") // Database
    api("ch.qos.logback", "logback-classic", "1.4.7") // Logging
}

setupPublishing(
    artifactId = "lazy-library",
    url = "https://lazy-library.srnyx.com",
    licenses = listOf(LicenseData.MIT),
    developers = listOf(DeveloperData.srnyx))
