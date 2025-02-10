plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}


group = "karbbone.todayversus"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("net.dv8tion:JDA:5.3.0") { // replace $version with the latest version
        // Optionally disable audio natives to reduce jar size by excluding `opus-java`
        // Gradle DSL:
        // exclude module: 'opus-java'
        // Kotlin DSL:
        // exclude(module="opus-java")
    }
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "karbbone.todayversus.Main"
    }
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.shadowJar {
    archiveBaseName.set("bot-discord")
    archiveClassifier.set("")
    archiveVersion.set("")
}


tasks.test {
    useJUnitPlatform()
}