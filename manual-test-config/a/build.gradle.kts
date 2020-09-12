plugins {
    id("io.heapy.gradle.properties")
}

props {
    versionFile = file("a.properties")
}

tasks.create("validate") {
    doLast {
        check(version == "3.14.15") { "Version is not 3.14.15" }
    }
}
