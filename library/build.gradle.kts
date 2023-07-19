import xyz.srnyx.gradlegalaxy.data.pom.DeveloperData
import xyz.srnyx.gradlegalaxy.data.pom.LicenseData
import xyz.srnyx.gradlegalaxy.utility.setupPublishing


application.mainClass.set("xyz.srnyx.lazylibrary.LazyLibrary")

dependencies {
    implementation("io.github.freya022", "BotCommands", "2.10.2") // Command framework
    implementation("org.spongepowered", "configurate-yaml", "4.1.2") // Data storage
    implementation("org.postgresql", "postgresql", "42.6.0") // Database
    implementation("com.zaxxer", "HikariCP", "5.0.1") // Database
    implementation("ch.qos.logback", "logback-classic", "1.4.7") // Logging
}

setupPublishing(
    artifactId = "lazy-library",
    url = "https://lazy-library.srnyx.com",
    licenses = listOf(LicenseData.MIT),
    developers = listOf(DeveloperData.srnyx))
