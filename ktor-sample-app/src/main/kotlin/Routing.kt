package com.victor

import com.victor.com.victor.BenfordLawChecker
import com.victor.com.victor.MyStringParser
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

            val parser = MyStringParser();
            val parsedString = parser.parse(inputString);

            val checker = BenfordLawChecker();
            checker.chiSquaredTest(parsedString, 0.05);

            call.respond(inputString)
        }
    }
}
