package thermo

import io.ktor.application.*
import io.ktor.html.respondHtml
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.html.*

fun main(args: Array<String>) {
    embeddedServer(Netty, 8080) {
        routing {
            get("/") {
                call.respondHtml {
                    head {
                        title { + "Hi from Thermo"}
                    }
                    body {
                        h1 {
                            +"A title"
                        }
                        p {
                            +"For body"
                        }
                    }
                }
            }
        }
    }.start(wait = true)
}
