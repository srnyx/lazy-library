import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.kotlin.dsl.withType

plugins {
    application
    `java-library`
    id("xyz.srnyx.gradle-galaxy") version "4.0.0"
    id("com.gradleup.shadow") version "9.6.1"
    id("dev.reformator.stacktracedecoroutinator") version "2.6.2"
}

group = "xyz.srnyx"
description = "A simple library for JDA Discord bots"

galaxy {
    repository {
        add(SRNYX_RELEASES, SRNYX_SNAPSHOTS)
    }

    discord {
        jda("6.6.0")
    }

    mavenPublishing {
        artifactId = "lazy-library"
        licenses.add(MIT)
        developers.add(SRNYX)

        publication { pom {
            url = "https://lazy-library.srnyx.com"
        } }
    }
}

dependencies {
    api("xyz.srnyx:java-utilities:afd7cad") // General Java utility library
    api("com.google.code.gson:gson:2.3.1") // Use this specific version for Java Utilities
    api("io.github.freya022:BotCommands:3.4.0") // Command framework
    api("org.spongepowered:configurate-yaml:4.2.0") // Config manager
    api("dev.freya02:jda-emojis:4.3.0") // Emojis
    implementation("ch.qos.logback:logback-classic:1.5.38") // Logging
    implementation("com.zaxxer:HikariCP:7.1.0") // Database
    implementation("org.flywaydb:flyway-core:12.3.0") // Database
    runtimeOnly("org.flywaydb:flyway-database-postgresql:12.3.0") // Database
    runtimeOnly("org.postgresql:postgresql:42.7.13") // Database
}

// Fix Java's service loading, which Flyway uses
tasks.withType<ShadowJar> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    
    mergeServiceFiles {
        include("META-INF/services/**")
    }
}

// Show warnings for missing Javadocs
tasks.withType<Javadoc> { (options as StandardJavadocDocletOptions).addStringOption("Xdoclint:all", "-quiet") }
