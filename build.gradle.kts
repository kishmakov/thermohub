import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "Thermo"
version = "1.0-SNAPSHOT"

val ktor_version = "1.1.2"

plugins {
    application
    kotlin("jvm") version "1.3.21"
}

repositories {
    jcenter()
    maven("http://kotlin.bintray.com/ktor")
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile>().all {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "thermo.MainKt"
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("io.ktor:ktor-server-netty:$ktor_version")
    compile("ch.qos.logback:logback-classic:1.2.3")
    testCompile(group = "junit", name = "junit", version = "4.12")
}
