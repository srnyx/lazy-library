import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import xyz.srnyx.gradlegalaxy.enums.Repository
import xyz.srnyx.gradlegalaxy.enums.repository
import xyz.srnyx.gradlegalaxy.utility.addCompilerArgs
import xyz.srnyx.gradlegalaxy.utility.setupJava


plugins {
    application
    id("xyz.srnyx.gradle-galaxy") version "1.1.2" apply false
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false
}

subprojects {
    apply(plugin = "application")
    apply(plugin = "xyz.srnyx.gradle-galaxy")
    apply(plugin = "com.github.johnrengelman.shadow")

    setupJava("xyz.srnyx", "3.0.0", "A simple library for JDA Discord bots", JavaVersion.VERSION_19)
    addCompilerArgs("-parameters")
    repository(Repository.MAVEN_CENTRAL, Repository.JITPACK)

    tasks.withType<ShadowJar> {
        dependsOn("distTar", "distZip")
    }
}
