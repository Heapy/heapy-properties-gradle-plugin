plugins {
    id("io.heapy.gradle.properties")
}

val a: String by project
val b: String by project
val c: String by project

tasks.create("validate") {
    check(a == "1") { "Property a is not 1" }
    check(b == "2") { "Property b is not 2" }
    check(c == "3") { "Property c is not 3" }

    doLast {
        check(version == "1.2.3") { "Version is not 1.2.3" }
    }
}
