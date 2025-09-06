package com.victor

import com.victor.dto.BenfordRequest
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

                val parser = AccountBalanceParser()
                val accountBalances = parser.parseToDoubles(request.accountBalances)

                val benford = BenfordChecker()
                val confidenceLevel = request.confidenceLevel.toDouble()
                val response = benford.chiSquaredTest(accountBalances, confidenceLevel)

                call.respond(response)
            } catch (e: Exception) {
                log.error("Error processing request", e)
                call.respondText("Error processing request: ${e.message}", status = io.ktor.http.HttpStatusCode.BadRequest)
            }

        }
    }
}
