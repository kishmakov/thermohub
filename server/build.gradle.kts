description = "Server side of Thermohub project"
group = "tech.thermohub"
version = "1.0-SNAPSHOT"

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath(kotlin("gradle-plugin"))
    }
}

plugins {
    application
    kotlin("jvm") version "1.3.21"
}

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

repositories {
    jcenter()
    maven("http://dl.bintray.com/kotlin/ktor")
}

sourceSets["main"].resources.srcDir("resources")

val ktor_version = "1.1.2"

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("io.ktor:ktor-server-netty:$ktor_version")
    compile("io.ktor:ktor-html-builder:$ktor_version")
    compile("io.ktor:ktor-websockets:$ktor_version")
    compile("ch.qos.logback:logback-classic:1.2.3")
    testCompile(group = "junit", name = "junit", version = "4.12")
    testCompile("io.ktor:ktor-server-test-host:$ktor_version")
}

