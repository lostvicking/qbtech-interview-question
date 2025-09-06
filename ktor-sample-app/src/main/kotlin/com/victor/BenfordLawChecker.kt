package com.victor

import org.apache.commons.math3.stat.inference.ChiSquareTest
import org.apache.commons.math3.distribution.ChiSquaredDistribution

class BenfordLawChecker {
    
    private val chiSquareTest = ChiSquareTest()
    
    /**
     * Performs a chi-squared test to determine if the given account balances follow Benford's Law
     * @param accountBalances List of account balance values to test
     * @param confidenceLevel The confidence level for the test (e.g., 0.05 for 95% confidence)
     * @return Boolean indicating whether the data follows Benford's Law at the given confidence level
     */
    fun chiSquaredTest(accountBalances: List<Double>, confidenceLevel: Double): Boolean {
        // Extract first digits from account balances
        val firstDigits = accountBalances
            .filter { it > 0 } // Only positive values
            .map { balance ->
                val firstDigit = balance.toString().first { it.isDigit() }.digitToInt()
                firstDigit
            }
            .filter { it in 1..9 } // Only digits 1-9
        
        if (firstDigits.isEmpty()) {
            throw IllegalArgumentException("No valid account balances found")
        }
        
        // Count occurrences of each first digit
        val observedCounts = LongArray(9) // Index 0 = digit 1, index 1 = digit 2, etc.
        firstDigits.forEach { digit ->
            observedCounts[digit - 1]++
        }
        
        // Calculate expected counts based on Benford's Law
        val totalCount = firstDigits.size.toDouble()
        val expectedCounts = DoubleArray(9) { digit ->
            val d = digit + 1 // Convert index to actual digit (1-9)
            totalCount * kotlin.math.log10(1.0 + 1.0 / d)
        }
        
        // Perform chi-squared test
        val nullHypothesisRejectedForWithConfidence = chiSquareTest.chiSquareTest(expectedCounts, observedCounts, confidenceLevel)
        
        // Return true if p-value is greater than significance level (data follows Benford's Law)
        return nullHypothesisRejectedForWithConfidence
    }
}
