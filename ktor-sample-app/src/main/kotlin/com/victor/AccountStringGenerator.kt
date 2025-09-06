package com.victor

import kotlin.random.Random

// can use this to test the API for receiving string with account balances
// TODO: move to test package?
class AccountStringGenerator {
    
    fun generateAccountBalanceString(isBenford: Boolean, numberAccounts: Int): String {
        val entries = mutableListOf<String>()
        
        for (i in 1..numberAccounts) {
            val accountName = "account$i"
            if (isBenford) {
                val accountBalance = generateBenfordBalance()
                entries.add("$accountName: $accountBalance")
            } else {
//                val accountBalance = generateRandomBalance()
                val accountBalance = generateUnbenfordBalance()
                entries.add("$accountName: $accountBalance")
            }
        }
        
        return entries.joinToString(", ")
    }

    private fun generateBenfordBalance(): String {
        // Generate a Benford-compliant balance
        val leadingDigit = Random.nextInt(1, 3)
        val magnitude = Random.nextInt(0, 5) // to create variability in size
        val restOfNumber = Random.nextDouble(0.0, 1.0) * Math.pow(10.0, magnitude.toDouble())
        val accountBalance = String.format("%.2f", leadingDigit * Math.pow(10.0, magnitude.toDouble()) + restOfNumber)
        return accountBalance
    }

    private fun generateUnbenfordBalance(): String {
        // Generate a Benford-compliant balance
        val leadingDigit = Random.nextInt(7, 9)
        val magnitude = Random.nextInt(0, 5) // to create variability in size
        val restOfNumber = Random.nextDouble(0.0, 1.0) * Math.pow(10.0, magnitude.toDouble())
        val accountBalance = String.format("%.2f", leadingDigit * Math.pow(10.0, magnitude.toDouble()) + restOfNumber)
        return accountBalance
    }

    private fun generateRandomBalance(): String {
        // Generate random balance between 100.00 and 90000.99
        val balance = Random.nextDouble(100.0, 90001.0)
        return String.format("%.2f", balance)
    }
}

fun main() {
    val generator = AccountStringGenerator()
    val result = generator.generateAccountBalanceString(true, 100)
    println(result)
}
