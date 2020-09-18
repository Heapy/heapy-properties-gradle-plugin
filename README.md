# Heapy-properties Gradle Plugin [![Build Status](https://travis-ci.com/Heapy/heapy-properties-gradle-plugin.svg?branch=master)](https://travis-ci.com/Heapy/heapy-properties-gradle-plugin) [![Gradle Plugin Portal](https://img.shields.io/maven-metadata/v/https/plugins.gradle.org/m2/io/heapy/gradle/properties/io.heapy.gradle.properties.gradle.plugin/maven-metadata.xml.svg?colorB=007ec6&label=gradle%20plugin)](https://plugins.gradle.org/plugin/io.heapy.gradle.properties)

1. Recursively reads `local.properties` and merges to project
2. Reads version from `build.properties`

## Install

```kotlin
plugins {
    id("io.heapy.gradle.properties").version("1.1.2")
}
```

## Configuration

Configure custom `build.properties` file name or location:

```kotlin
props {
    versionFile = file("my.properties")
}
```

This configuration will be applied to all subprojects.

## Examples

Checkout [manual-test](./manual-test) and [manual-test-config](./manual-test-config) for examples.
