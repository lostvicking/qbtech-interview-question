package com.victor

import kotlin.random.Random

// can use this to test the API for receiving string with account balances
// TODO: move to test package?
class AccountStringGenerator {
    
    fun generateAccountBalanceString(numberAccounts: Int): String {
        val entries = mutableListOf<String>()
        
        for (i in 1..numberAccounts) {
            val accountName = "account$i"
            val accountBalance = generateRandomBalance()
            entries.add("$accountName: $accountBalance")
        }
        
        return entries.joinToString(", ")
    }
    
    private fun generateRandomBalance(): String {
        // Generate random balance between 100.00 and 90000.99
        val balance = Random.nextDouble(100.0, 90001.0)
        return String.format("%.2f", balance)
    }
}

fun main() {
    val generator = AccountStringGenerator()
    val result = generator.generateAccountBalanceString(100)
    println(result)
}
