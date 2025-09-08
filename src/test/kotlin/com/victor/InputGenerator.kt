package com.victor

import kotlin.math.ln
import kotlin.random.Random

// can use this to test the API for receiving string with account balances
class InputGenerator {
    
    // Benford's Law probabilities for digits 1-9
    val benfordProbabilities = (1..9).map { digit ->
        ln(1.0 + 1.0 / digit) / ln(10.0)
    }
    
    // Cumulative probabilities for sampling
    private val cumulativeProbabilities = run {
        var cumSum = 0.0
        benfordProbabilities.map { prob ->
            cumSum += prob
            cumSum
        }
    }
    
    fun generateAccountBalanceString(isBenford: Boolean, numberAccounts: Int): String {
        val entries = mutableListOf<String>()
        
        for (i in 1..numberAccounts) {
            val accountName = "account$i"
            val accountBalance = if (isBenford) {
                generateBenfordBalance()
            } else {
                generateUnbenfordBalance()
            }
            entries.add("$accountName: $accountBalance")
        }
        
        return entries.joinToString(", ")
    }

    private fun generateBenfordFirstDigit(): Int {
        val rand = Random.nextDouble()
        for (i in cumulativeProbabilities.indices) {
            if (rand <= cumulativeProbabilities[i]) {
                return i + 1 // Return digit 1-9
            }
        }
        return 9 // Fallback
    }

    private fun generateBenfordBalance(): String {
        // Generate a Benford-compliant balance using proper distribution
        val firstDigit = generateBenfordFirstDigit()
        val magnitude = Random.nextInt(1, 6) // 1-5 additional digits
        
        // Generate remaining digits without affecting the first digit
        // This ensures the first digit is preserved from Benford distribution
        val remainingDigits = Random.nextDouble(0.0, 1.0) // 0.0 to 0.999...
        val balance = (firstDigit + remainingDigits) * Math.pow(10.0, magnitude.toDouble())
        
        // Format with comma as decimal separator to match expected format
        return String.format("%.2f", balance).replace(".", ",")
    }

    private fun generateUnbenfordBalance(): String {
        // Generate balance that doesn't follow Benford's Law (favor higher digits)
        val leadingDigit = Random.nextInt(7, 10) // Favor digits 7, 8, 9
        val magnitude = Random.nextInt(1, 6)
        val restOfNumber = Random.nextDouble(0.0, 1.0) * Math.pow(10.0, magnitude.toDouble())
        val balance = leadingDigit * Math.pow(10.0, magnitude.toDouble()) + restOfNumber
        
        return String.format("%.2f", balance).replace(".", ",")
    }

    private fun generateRandomBalance(): String {
        // Generate random balance between 100.00 and 90000.99
        val balance = Random.nextDouble(100.0, 90001.0)
        return String.format("%.2f", balance).replace(".", ",")
    }
    
    fun generateBenfordTestData(confidenceLevel: String = "0.05", numberAccounts: Int = 100): String {
        val accountBalances = generateAccountBalanceString(false, numberAccounts)
        return """
        {
            "confidenceLevel": "$confidenceLevel",
            "accountBalances": "$accountBalances"
        }
        """.trimIndent()
    }
}

fun main() {
    val generator = InputGenerator()
    
    // Generate the test data in the required format
    val testData = generator.generateBenfordTestData(numberAccounts = 100)
    println(testData)
    
    // Also show distribution analysis
    val balanceString = generator.generateAccountBalanceString(true, 100)
    val regex = Regex("""account\d+:\s*(\d+(?:,\d+)?)""")
    val amounts = regex.findAll(balanceString).map { it.groupValues[1] }.toList()
    val firstDigits = amounts.map { it.replace(",", ".").first().digitToInt() }
    
    println("\nFirst digit distribution analysis:")
    for (digit in 1..9) {
        val count = firstDigits.count { it == digit }
        val percentage = (count.toDouble() / firstDigits.size) * 100
        val expected = generator.benfordProbabilities[digit - 1] * 100
        println("Digit $digit: $count (${String.format("%.1f", percentage)}%) - Expected: ${String.format("%.1f", expected)}%")
    }
}
