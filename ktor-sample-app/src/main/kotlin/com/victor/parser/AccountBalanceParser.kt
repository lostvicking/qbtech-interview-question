package com.victor.parser

class AccountBalanceParser() {
    fun parseToDoubles(inputString: String): List<Double> {
        // Parse strings like "account1: 20,68, account2: 2,81, account3: 14971,28"
        // Handle both comma and dot as decimal separators
        val regex = Regex("""account\d+:\s*(\d+(?:[,.]\d+)?)""")
        return regex.findAll(inputString)
            .map { matchResult ->
                val numberString = matchResult.groupValues[1]
                // Replace comma with dot for proper Double parsing
                numberString.replace(",", ".").toDouble()
            }
            .toList()
    }
}
