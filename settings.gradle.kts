pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("de.fayard.refreshVersions") version "0.10.1"
}

rootProject.name = "spectral"

include(":spectral-client")
include(":spectral-api")
include(":runescape-api")
include(":common")
include(":logger")
include(":runescape-hooks")
include(":cache")
include(":launcher")
include(":injector")

include(":spectral-gradle-plugin")
include(":spectral-plugin")