package com.victor

import com.victor.dto.BenfordRequest
import com.victor.dto.BenfordResponse
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.serialization.kotlinx.json.json
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertTrue

class ApplicationTest {

    // integration tests would go here
    // https://ktor.io/docs/server-testing.html#test-app

    @Test
    fun testRoot() = testApplication {
        application {
            module()
        }

        client = createClient {
            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                json()
            }
        }

        val response = client.post("/parse-string") {
            contentType(ContentType.Application.Json)
            setBody(
                BenfordRequest(
                    accountBalances = "123, 456, 789, 101, 112, 131, 415, 161, 718, 192",
                    confidenceLevel = "0.05"
                )
            )
        }

        assertTrue(response.status.isSuccess())
        val benfordResponse = response.body<BenfordResponse>()
        assertTrue { benfordResponse.followsBenfordsLaw }
    }
}