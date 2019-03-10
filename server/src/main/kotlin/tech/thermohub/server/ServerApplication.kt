package tech.thermohub.server

import io.ktor.application.*
import io.ktor.html.respondHtml
import io.ktor.routing.*
import kotlinx.html.*

fun Application.main() {
    ServerApplication().apply { main() }
}

class ServerApplication {
    fun Application.main() {
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
    }
}
