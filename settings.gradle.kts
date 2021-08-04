plugins {
    id("de.fayard.refreshVersions") version "0.10.1"
}

rootProject.name = "spectral"

include(":spectral-client")
include(":spectral-api")
include(":runescape-api")
include(":common")
include(":logger")
include(":spectral-plugin")
include(":runescape-hooks")
include(":cache")
include(":launcher")
include(":injector")