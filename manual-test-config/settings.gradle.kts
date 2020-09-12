includeBuild("../")

include(":child")
include(":a", ":a:b", ":a:b:c")
