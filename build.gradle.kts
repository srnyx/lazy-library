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

    setupJava("xyz.srnyx", "1.0.1", javaVersion = JavaVersion.VERSION_19)
    addCompilerArgs("-parameters")
    repository(Repository.MAVEN_CENTRAL, Repository.JITPACK)
    dependencies.implementation("net.dv8tion", "JDA", "5.0.0-beta.10")

    tasks.withType<ShadowJar> {
        dependsOn("distTar", "distZip")
    }
}
