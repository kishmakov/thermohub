description = "Server side of Thermo project"

plugins {
    application
    kotlin("jvm") version "1.3.21"
}
//
group = "Thermo"
version = "1.0-SNAPSHOT"

val ktor_version = "1.1.2"

repositories {
    jcenter()
    maven("http://dl.bintray.com/kotlin/ktor")
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

application {
    mainClassName = "thermo.MainKt"
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("io.ktor:ktor-server-netty:$ktor_version")
    compile("io.ktor:ktor-html-builder:$ktor_version")
    compile("ch.qos.logback:logback-classic:1.2.3")
    testCompile(group = "junit", name = "junit", version = "4.12")
}
