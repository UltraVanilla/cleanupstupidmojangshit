import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    `java-library`
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
}
repositories {
    mavenCentral()
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
}
bukkit {
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    main = "lordpipe.cleanupstupidmojangshit.CleanUpStupidMojangShit"
    apiVersion = "1.17"
    authors = listOf("lordpipe")
}

