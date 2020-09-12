plugins {
    id("io.heapy.gradle.properties")
}

props {
    versionFile = file("mybuild.properties")
}

tasks.create("validate") {
    doLast {
        check(version == "3.2.1") { "Version is not 3.2.1" }
    }
}
