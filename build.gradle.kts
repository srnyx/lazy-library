import xyz.srnyx.gradlegalaxy.data.pom.DeveloperData
import xyz.srnyx.gradlegalaxy.data.pom.LicenseData
import xyz.srnyx.gradlegalaxy.enums.Repository
import xyz.srnyx.gradlegalaxy.enums.repository
import xyz.srnyx.gradlegalaxy.utility.addCompilerArgs
import xyz.srnyx.gradlegalaxy.utility.setupJava
import xyz.srnyx.gradlegalaxy.utility.setupPublishing


plugins {
    application
    `java-library`
    id("xyz.srnyx.gradle-galaxy") version "1.2.3"
    id("com.gradleup.shadow") version "8.3.0"
}

setupJava("xyz.srnyx", "3.1.0", "A simple library for JDA Discord bots", JavaVersion.VERSION_19)
application.mainClass.set("xyz.srnyx.lazylibrary.LazyLoader")
addCompilerArgs("-parameters")

repository(Repository.ALESSIO_DP, Repository.MAVEN_CENTRAL, Repository.JITPACK)
dependencies {
    implementation("net.byteflux", "libby-core", "1.3.1")
    compileOnlyApi("org.jetbrains", "annotations", "24.1.0")
    compileOnlyApi("net.dv8tion", "JDA", "5.0.2")
    compileOnlyApi("xyz.srnyx", "java-utilities", "a073202b43")
    compileOnlyApi("io.github.freya022", "BotCommands", "2.10.3")
    compileOnlyApi("org.spongepowered", "configurate-yaml", "4.1.2")
    compileOnly("org.postgresql", "postgresql", "42.7.3")
    compileOnly("com.zaxxer", "HikariCP", "5.1.0")
    compileOnly("ch.qos.logback", "logback-classic", "1.5.6")
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
