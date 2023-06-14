import me.dkim19375.dkimgradle.enums.Repository
import me.dkim19375.dkimgradle.enums.maven
import me.dkim19375.dkimgradle.util.addBuildShadowTask
import me.dkim19375.dkimgradle.util.setTextEncoding

plugins {
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.github.dkim19375.dkim-gradle") version "1.2.1"
}

// This doesn't actually do anything, but it's required for the application plugin
application.mainClass.set("xyz.srnyx.lazylibrary.LazyLibrary")

subprojects {
    version = "1.0.1"

    apply(plugin = "application")
    apply(plugin = "com.github.johnrengelman.shadow")
    apply(plugin = "io.github.dkim19375.dkim-gradle")

    repositories {
        maven(Repository.MAVEN_CENTRAL, Repository.JITPACK)
    }

    dependencies {
        implementation("net.dv8tion", "JDA", "5.0.0-beta.10") // JDA
    }

    addBuildShadowTask()
    setTextEncoding()

    tasks {
        // Remove '-all' from the JAR file name
        shadowJar {
            archiveClassifier.set("")
        }

        // Parameter names
        compileJava {
            options.compilerArgs.plusAssign("-parameters")
        }
    }
}
