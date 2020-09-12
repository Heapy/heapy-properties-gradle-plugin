# Heapy-properties Gradle Plugin

1. Recursively reads `local.properties` and merges to project
2. Reads version from `build.properties`

## Install

```kotlin
plugins {
    id("io.heapy.gradle.properties")
}
```

## Configuration

Configure custom `build.properties` file name or location:

```kotlin
configure<PropertiesPluginExtension> {
    versionFile = file("my.properties")
}
```
