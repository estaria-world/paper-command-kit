import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.0"
    `maven-publish`
}

group = "world.estaria"
version = "1.1.0"

repositories {
    mavenCentral()

    // minecraft repositories
    maven("https://repo.papermc.io/repository/maven-public/")

    // estaria dependencies
    maven {
        name = "estaria"
        url = uri("https://repo.estaria.world/releases")
        credentials(PasswordCredentials::class.java)
    }
}

private var incendoCloudVersion = "2.0.0-rc.2"

dependencies {
    // avionik dependencies
    compileOnly("world.avionik:config-kit:1.0.2")
    compileOnly("world.avionik:minecraft-common:1.0.1")

    // estaria dependencies
    compileOnly("world.estaria:translation-api:1.1.0")
    compileOnly("world.estaria.coco.paper:coco-paper-api:1.21.1-R0.1-SNAPSHOT")

    // cloud dependencies
    api("org.incendo:cloud-core:$incendoCloudVersion")
    api("org.incendo:cloud-paper:2.0.0-beta.9")
    api("org.incendo:cloud-annotations:$incendoCloudVersion")
    api("org.incendo:cloud-minecraft-extras:2.0.0-beta.9")
}

tasks.named("shadowJar", ShadowJar::class) {
    mergeServiceFiles()
    dependencies {
        exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib"))
        exclude(dependency("org.jetbrains.kotlin:kotlin-reflect"))
        exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib-common"))
        exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib-jdk7"))
        exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib-jdk8"))
    }
}

publishing {
    repositories {
        maven {
            name = "estaria"
            url = uri("https://repo.estaria.world/releases")
            credentials(PasswordCredentials::class.java)
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}