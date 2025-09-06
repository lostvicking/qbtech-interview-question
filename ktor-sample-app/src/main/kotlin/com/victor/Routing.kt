package com.victor

import com.victor.math.BenfordChecker
import com.victor.parser.AccountStringParser
import io.ktor.server.application.*
import io.ktor.server.request.receiveText
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        post("/parse-string") {
            val inputString = call.receiveText()
            log.info("incoming string: $inputString")

            val parser = AccountStringParser();
            val parsedString = parser.parseToDoubles(inputString);

            val checker = BenfordChecker();
            checker.chiSquaredTest(parsedString, 0.05);

            call.respond(inputString)
        }
    }
}
