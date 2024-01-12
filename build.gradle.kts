plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.summitrealms"
version = "1.0-BETA"
description = "A scheduled rebooting plugin for Pterodactyl hosted velocity servers"

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }

    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
    }

    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    compileOnly(libs.velocity)
    implementation(libs.cron4j)
}

val targetJavaVersion = 17
java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
    }
}

tasks {
    withType<JavaCompile>().configureEach {
        if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
            options.release.set(targetJavaVersion)
        }
    }

    shadowJar {
        archiveFileName.set("${project.name}-${project.version}.jar")
    }
}

