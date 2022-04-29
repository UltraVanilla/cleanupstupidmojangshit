pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

include("common")
include("1_18_R1")
include("1_18_R2")
rootProject.name = "CleanUpStupidMojangShit"
