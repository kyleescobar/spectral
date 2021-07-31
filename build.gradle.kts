plugins {
    kotlin("jvm") version "1.5.10"
}

tasks.withType<Wrapper> {
    gradleVersion = "7.1.1"
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    group = "org.spectralpowered"
    version = "0.0.1"

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation(kotlin("stdlib"))
        implementation("org.tinylog:tinylog-api-kotlin:_")
        implementation("org.tinylog:tinylog-impl:_")
    }
}