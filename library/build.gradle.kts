import xyz.srnyx.gradlegalaxy.data.pom.DeveloperData
import xyz.srnyx.gradlegalaxy.data.pom.LicenseData
import xyz.srnyx.gradlegalaxy.utility.setupPublishing


application.mainClass.set("xyz.srnyx.lazylibrary.LazyLibrary")

dependencies {
    compileOnly("net.dv8tion", "JDA", "5.0.0-alpha.21") // JDA
    implementation("xyz.srnyx", "java-utilities", "1.0.0") // General Java utility library
    implementation("xyz.srnyx", "magic-mongo", "1.0.0") // MongoDB framework
    implementation("io.github.freya022", "BotCommands", "2.10.2") // Command framework
    implementation("org.spongepowered", "configurate-yaml", "4.1.2") // Config manager
    implementation("org.postgresql", "postgresql", "42.6.0") // Database
    implementation("com.zaxxer", "HikariCP", "5.0.1") // Database
    implementation("ch.qos.logback", "logback-classic", "1.4.11") // Logging
}

setupPublishing(
    artifactId = "lazy-library",
    url = "https://lazy-library.srnyx.com",
    licenses = listOf(LicenseData.MIT),
    developers = listOf(DeveloperData.srnyx))
