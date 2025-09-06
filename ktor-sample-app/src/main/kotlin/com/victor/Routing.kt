package com.victor

import com.victor.dto.BenfordRequest
import com.victor.dto.ParseStringResponse
import com.victor.math.BenfordChecker
import com.victor.parser.AccountBalanceParser
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        post("/parse-string") {
            try {
                val request = call.receive<BenfordRequest>()
                log.info("incoming request: $request")

                val parser = AccountBalanceParser()
                val parsedNumbers = parser.parseToDoubles(request.accountBalances)

                val checker = BenfordChecker()
                val confidenceLevel = request.confidenceLevel.toDouble()
                val response = checker.chiSquaredTest(parsedNumbers, confidenceLevel)

                call.respond(response)
            } catch (e: Exception) {
                log.error("Error processing request", e)
                call.respondText("Error processing request: ${e.message}", status = io.ktor.http.HttpStatusCode.BadRequest)
            }

        }
    }
}
