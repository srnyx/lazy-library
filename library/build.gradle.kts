plugins {
    `java-library`
    `maven-publish`
}

application.mainClass.set("xyz.srnyx.lazylibrary.LazyLibrary")

dependencies {
    api("io.github.freya022", "BotCommands", "2.10.0") // Command framework
    api("org.spongepowered", "configurate-yaml", "4.1.2") // Data storage
    api("org.postgresql", "postgresql", "42.6.0") // Database
    api("com.zaxxer", "HikariCP", "5.0.1") // Database
    api("ch.qos.logback", "logback-classic", "1.4.6") // Logging
}

// Javadoc JAR task
java {
    sourceCompatibility = JavaVersion.VERSION_19
    targetCompatibility = JavaVersion.VERSION_19
    withJavadocJar()
}

// Maven publishing for Jitpack
publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.github.srnyx"
            artifact(tasks["shadowJar"])
            artifact(tasks["javadocJar"])
        }
    }
}
