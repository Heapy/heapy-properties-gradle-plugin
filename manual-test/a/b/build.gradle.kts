plugins {
    id("io.heapy.gradle.properties")
}

val a: String by project
val b: String by project
val c: String by project

tasks.create("validate") {
    check(a == "2") { "Property a is not 2" }
    check(b == "3") { "Property b is not 3" }
    check(c == "4") { "Property c is not 4" }

    doLast {
        check(version == "1.2.3") { "Version is not 1.2.3" }
    }
}
