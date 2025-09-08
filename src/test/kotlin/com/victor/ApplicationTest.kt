package com.victor

import com.victor.dto.BenfordResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse


class ApplicationTest {

    // integration tests would go here
    // https://ktor.io/docs/server-testing.html#test-app

    @Test
    fun testRoot_whenAccountBalancesNotBenford_shouldReturnFalse() = testApplication {
        application {
            module()
        }

        client = createClient {
            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                json()
            }
        }

        val response = client.post("/benford-test-of-fit") {
            contentType(ContentType.Application.Json)
            setBody("{" +
                    "    \"confidenceLevel\": \"0.05\",\n" +
                    "    \"accountBalances\": \"account1: 90,68, account2: 92,81, account3: 14971,28\"\n" +
                    "}"
            )
        }

        assertTrue(response.status.isSuccess())
        val benfordResponse = response.body<BenfordResponse>()
        assertFalse { benfordResponse.followsBenfordsLaw }
    }
}