plugins {
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

// This doesn't actually do anything, but it's required for the application plugin
application.mainClass.set("xyz.srnyx.lazylibrary.LazyLibrary")

// Prevent java tasks from running for root project
tasks {
    classes { enabled = false }
    jar { enabled = false }
    startScripts { enabled = false }
    distTar { enabled = false }
    distZip { enabled = false }
    shadowJar { enabled = false }
    startShadowScripts { enabled = false }
    shadowDistTar { enabled = false }
    shadowDistZip { enabled = false }
    assemble { enabled = false }
    testClasses { enabled = false }
    check { enabled = false }
    build { enabled = false }
}

subprojects {
    version = "1.0.1"

    apply(plugin = "application")
    apply(plugin = "com.github.johnrengelman.shadow")

    repositories {
        mavenCentral() // org.spongepowered:configurate-yaml, io.github.freya022:BotCommands, org.postgresql:postgresql, com.zaxxer:HikariCP, ch.qos.logback:logback-classic
        maven("https://jitpack.io") // io.github.freya022:BotCommands
    }

    dependencies {
        implementation("net.dv8tion", "JDA", "5.0.0-beta.10") // JDA
    }

    tasks {
        // Remove '-all' from the JAR file name
        shadowJar {
            archiveClassifier.set("")
        }

        // Make 'gradle build' run 'gradle shadowJar'
        build {
            dependsOn("shadowJar")
        }

        // Text encoding and parameter names
        compileJava {
            options.encoding = "UTF-8"
            options.compilerArgs.plusAssign("-parameters")
        }

        // Disable unnecessary tasks
        classes { enabled = false }
        jar { enabled = false }
        compileTestJava { enabled = false }
        processTestResources { enabled = false }
        testClasses { enabled = false }
        test { enabled = false }
        check { enabled = false }
        distTar { enabled = false }
        distZip { enabled = false }
        shadowDistTar { enabled = false }
        shadowDistZip { enabled = false }
    }
}
