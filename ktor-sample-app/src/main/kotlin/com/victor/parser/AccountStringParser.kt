package com.victor.parser

class AccountStringParser() {
    fun parseToDoubles(inputString: String): List<Double> {
        // Parse strings like "account1: 123.45, account2: 67.89, account3: 0.12"
        val regex = Regex("""account\d+:\s*(\d+\.?\d*)""")
        return regex.findAll(inputString)
            .map { it.groupValues[1].toDouble() }
            .toList()
    }
}