tasks.create("validate") {
    doLast {
        check(version == "3.14.15") { "Version is not 3.14.15" }
    }
}
