import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "lordpipe.cleanupstupidmojangshit"
version = "1.0.0"
description = "Plugin to remove broken FilteredText NBT data from signs"

dependencies {
    implementation(project(":common"))
    implementation(files("1_18_R1/build/libs/1_18_R1-unspecified.jar"))
    implementation(files("1_18_R2/build/libs/1_18_R2-unspecified.jar"))
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}
tasks {
    named<ShadowJar>("shadowJar") {
        dependsOn(":1_18_R1:reobfJar")
        dependsOn(":1_18_R2:reobfJar")
        mergeServiceFiles()
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}

