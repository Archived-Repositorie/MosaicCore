plugins {
    id("fabric-loom") version "1.3-SNAPSHOT"
    id("maven-publish")
    id("org.jetbrains.kotlin.jvm")
    id("com.ncorti.ktfmt.gradle") version "0.12.0"
}

loom {
    serverOnlyMinecraftJar()
}

val sourceCompatibility = JavaVersion.VERSION_17
val targetCompatibility = JavaVersion.VERSION_17

val archivesBaseName = project.properties["archivesBaseName"].toString()

version = project.properties["mod_version"].toString()
group = project.properties["maven_group"].toString()

repositories {
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    minecraft("com.mojang:minecraft:${project.properties["minecraft_version"]}")
    mappings("net.fabricmc:yarn:${project.properties["minecraft_version"]}+build.${project.properties["yarn_mappings"]}:v2")
    modImplementation("net.fabricmc:fabric-loader:${project.properties["loader_version"]}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${project.properties["fabric_kotlin_version"]}+kotlin.${project.properties["kotlin_version"]}")
    testImplementation("net.fabricmc:fabric-loader-junit:${project.properties["loader_version"]}")

    val mixinExtras = "com.github.LlamaLad7:MixinExtras:${project.properties["mixin_extras"]}"

    implementation(mixinExtras)
    annotationProcessor(mixinExtras)
    include(mixinExtras)
}

tasks {

    processResources {
        expand(project.properties)
    }

    jar {
        from("LICENSE") {
            rename { "${it}_${project.properties["archivesBaseName"]}"}
        }
    }

    test {
        useJUnitPlatform()
        systemProperty("fabric.side", "server")
    }

    withType<JavaCompile>().configureEach {
        options.release = 17
    }
}

java {
    withSourcesJar()
}

ktfmt {
    kotlinLangStyle()
}


