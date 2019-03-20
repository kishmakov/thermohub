import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("multiplatform") version "1.3.21"
}

repositories {
    jcenter()
    maven("http://dl.bintray.com/kotlin/ktor")
    mavenCentral()
}

val ktor_version = "1.1.3"
val logback_version = "1.2.3"

kotlin {
    js()
    jvm()

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation("io.ktor:ktor-html-builder:$ktor_version")
                implementation("io.ktor:ktor-websockets:$ktor_version")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
                implementation("io.ktor:ktor-server-test-host:$ktor_version")
            }
        }
    }
}

val copyResources by tasks.creating(Copy::class) {
    dependsOn(tasks["build"])
    into("build/resources/main")
    from("build/processedResources/jvm/main")
}

val copyClasses by tasks.creating(Copy::class) {
    dependsOn(copyResources)
    into("build/classes/kotlin/main")
    from("build/classes/kotlin/jvm/main")
}

tasks["run"].dependsOn(copyClasses)

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
    dependencies{
        implementation("io.ktor:ktor-server-netty:$ktor_version")
        implementation("ch.qos.logback:logback-classic:1.2.3")
    }
}
