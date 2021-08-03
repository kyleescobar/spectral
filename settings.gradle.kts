pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven { url = uri("https://repo.nokee.dev/release") }
    }

    resolutionStrategy {
        eachPlugin {
            if(requested.id.id.startsWith("dev.nokee.")) {
                useModule("${requested.id.id}:${requested.id.id}.gradle.plugin:0.4.0")
            }
        }
    }
}

plugins {
    id("de.fayard.refreshVersions") version "0.10.1"
}

rootProject.name = "spectral"

include(":common")
include(":spectral-client")
include(":spectral-api")
include(":runescape-api")
include(":injector")
include(":spectral-launcher")
include(":spectral-logger")
