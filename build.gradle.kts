plugins {
    id("java-library")
    id("xyz.jpenilla.run-paper") version "3.0.2"
}

group = "com.nikoverflow"
version = project.property("plugin_version").toString()

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:26.1.2.build.+")
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(25))

tasks {
    runServer {
        minecraftVersion("26.1.2")
        jvmArgs("-Xms2G", "-Xmx2G", "-DPaper.IgnoreJavaVersion=true")
    }

    processResources {
        inputs.property("plugin_version", project.property("plugin_version"))
        filesMatching("plugin.yml") {
            expand(
                "plugin_version" to inputs.properties["plugin_version"].toString(),
            )
        }
    }
}
