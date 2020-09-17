plugins {
    `kotlin-dsl`
    id("com.gradle.plugin-publish").version("0.12.0")
    id("io.heapy.gradle.properties").version("1.1.1")
    id("java-gradle-plugin")
}

group = "io.heapy.gradle.properties"

repositories {
    jcenter()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.2")
}

tasks {
    test {
        useJUnitPlatform()
    }
}

gradlePlugin {
    plugins {
        create("propertiesPlugin") {
            id = "io.heapy.gradle.properties"
            implementationClass = "io.heapy.gradle.properties.PropertiesPlugin"
            displayName = "Heapy-properties Gradle Plugin"
            description = """
                This plugin used to override gradle.properties with local.properties
                for development purposes. As well as externalize version to build.properties file,
                to keep gradle.properties clean.
            """.trimIndent()
        }
    }
}

pluginBundle {
    website = "https://github.com/Heapy/heapy-properties-gradle-plugin"
    vcsUrl = "https://github.com/Heapy/heapy-properties-gradle-plugin"
    tags = listOf("kotlin", "properties", "local", "build")
}
