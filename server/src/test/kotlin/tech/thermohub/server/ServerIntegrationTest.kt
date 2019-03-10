package tech.thermohub.server

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertTrue

class ServerIntegrationTest {

    @Test
    fun testMainPageServing() {
        withTestApplication(Application::main) {
            handleRequest(HttpMethod.Get, "/").apply {
            assertTrue { response.content!!.contains("Thermo") }
        }

        }

    }

}