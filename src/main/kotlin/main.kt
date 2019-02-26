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
                        title { + "Thermo"}
                    }
                    body {
                        h1 {
                            id="first_header"
                            +"Fluids"
                        }

                        select {
                            for (fluid in fluidList()) {
                                option {
                                    id = fluid
                                    +fluid
                                }
                            }
                        }

                    }
                }
            }
        }
    }.start(wait = true)
}
