plugins {
    id("java-library")
    id("xyz.jpenilla.run-paper") version "3.0.2"
    id("com.modrinth.minotaur") version "2.9.0"
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

modrinth {
    token.set(project.findProperty("modrinth_token")?.toString() ?: "")
    projectId.set("ultimate-challenges")
    versionType.set("release")
    versionName.set("UltimateChallenges ${project.property("plugin_version").toString()}")
    uploadFile.set(tasks.jar)
    gameVersions.addAll(project.property("minecraft_version").toString())
    loaders.add("paper")
    syncBodyFrom.set(rootProject.file("README.md").readText(Charsets.UTF_8))
    changelog.set(rootProject.file("CHANGELOG.md").readText(Charsets.UTF_8))
}
