import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.0"
    `maven-publish`
}

group = "world.estaria"
version = "1.0.6"

repositories {
    mavenCentral()

    // minecraft repositories
    maven("https://repo.papermc.io/repository/maven-public/")

    // estaria dependencies
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/estaria-world/kube-configmap-kit")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
        }
    }
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/estaria-world/translation")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
        }
    }
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/estaria-world/github-file-manager")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
        }
    }
}

private var incendoCloudVersion = "2.0.0-beta.2"

dependencies {
    // avionik dependencies
    compileOnly("world.avionik:config-kit:1.0.2")
    compileOnly("world.avionik:minecraft-common:1.0.1")

    // estaria dependencies
    compileOnly("world.estaria:kube-configmap-kit:1.0.4")
    compileOnly("world.estaria:translation-api:1.1.0")

    // paper dependencies
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")

    // cloud dependencies
    api("org.incendo:cloud-core:$incendoCloudVersion")
    api("org.incendo:cloud-paper:$incendoCloudVersion")
    api("org.incendo:cloud-annotations:$incendoCloudVersion")
    api("org.incendo:cloud-minecraft-extras:$incendoCloudVersion")

    // kubernetes dependencies
    api("io.fabric8:kubernetes-client:6.12.1")
}

tasks.named("shadowJar", ShadowJar::class) {
    mergeServiceFiles()
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/estaria-world/paper-command-kit")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}