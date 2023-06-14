import me.dkim19375.dkimgradle.util.setLanguageVersion

plugins {
    `java-library`
    `maven-publish`
}

application.mainClass.set("xyz.srnyx.lazylibrary.LazyLibrary")

dependencies {
    api("io.github.freya022", "BotCommands", "2.10.2") // Command framework
    api("org.spongepowered", "configurate-yaml", "4.1.2") // Data storage
    api("org.postgresql", "postgresql", "42.6.0") // Database
    api("com.zaxxer", "HikariCP", "5.0.1") // Database
    api("ch.qos.logback", "logback-classic", "1.4.7") // Logging
}

setLanguageVersion("19")

// Javadoc JAR task
java {
    withJavadocJar()
}

// Maven publishing for Jitpack
publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.github.srnyx"
            from(components["java"])
        }
    }
}
