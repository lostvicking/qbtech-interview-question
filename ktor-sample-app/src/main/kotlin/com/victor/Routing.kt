package com.victor

import com.victor.dto.ParseStringRequest
import com.victor.dto.ParseStringResponse
import com.victor.math.BenfordChecker
import com.victor.parser.AccountStringParser
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.serialization.*
import io.ktor.server.request.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        post("/parse-string") {
            try {
                val request = call.receive<ParseStringRequest>()
                log.info("incoming request: $request")

                val parser = AccountStringParser()
                val parsedNumbers = parser.parseToDoubles(request.accountData)

                val checker = BenfordChecker()
                val confidenceLevel = request.confidenceLevel.toDouble()
                val followsBenfordsLaw = checker.chiSquaredTest(parsedNumbers, confidenceLevel)

                val response = ParseStringResponse(
                    followsBenfordsLaw = followsBenfordsLaw,
                    confidenceLevel = confidenceLevel,
                    parsedNumbers = parsedNumbers,
                    accountData = request.accountData
                )

                call.respond(response)
            } catch (e: Exception) {
                log.error("Error processing request", e)
                call.respondText("Error processing request: ${e.message}", status = io.ktor.http.HttpStatusCode.BadRequest)
            }

        }
    }
}
