plugins {
    kotlin("jvm") version "1.5.21"
    `maven-publish`
}

tasks.withType<Wrapper> {
    gradleVersion = "7.1.1"
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")

    group = "org.spectralpowered"
    version = "0.0.1"

    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        implementation(kotlin("stdlib"))
        implementation(kotlin("reflect"))
    }


    publishing {
        repositories {
            mavenLocal()
        }

        publications {
            create<MavenPublication>("maven") {
                groupId = "org.spectralpowered"
                artifactId = "${project.name}"
                version = "${project.version}"
                from(components["java"])
            }
        }
    }
}