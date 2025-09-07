package com.victor.math

import com.victor.dto.BenfordResponse
import org.apache.commons.math3.stat.inference.ChiSquareTest
import kotlin.math.log10

class BenfordChecker {

    private val chiSquareTest = ChiSquareTest()

    /**
     * Performs a chi-squared test to determine if the given account balances follow Benford's Law
     * @param accountBalances List of account balance values to test
     * @param confidenceLevel The confidence level for the test (e.g., 0.05 for 95% confidence)
     * @return Boolean indicating whether the data follows Benford's Law at the given confidence level
     */
    fun chiSquaredTest(accountBalances: List<Double>, confidenceLevel: Double): BenfordResponse {
        // Extract first digits from account balances
        val firstDigits = getFirstDigits(accountBalances) // Only digits 1-9

        if (firstDigits.isEmpty()) {
            throw IllegalArgumentException("No valid account balances found")
        }

        // Count occurrences of each first digit
        val actualDistributionOfDigits = calculateActualDistribution(firstDigits) // Index 0 = digit 1, index 1 = digit 2, etc.

        // Calculate expected counts based on Benford's Law
        val expectedDistributionOfDigits = calculateExpectedDistribution(firstDigits)

        // Perform chi-squared test
        val nullHypothesisRejected = chiSquareTest.chiSquareTest(expectedDistributionOfDigits, actualDistributionOfDigits, confidenceLevel)

        //When the null hypothesis is rejected, it means:
        //
        //1. The statistical test found significant evidence that the observed distribution of first digits differs from what Benford's Law predicts
        //2. The p-value was less than the significance level (e.g., p < 0.05), indicating the difference is unlikely to be due to random chance
        //3. We conclude the data does NOT follow Benford's Law

        // we invert the value because if the null hypothesis is rejected, the data does NOT follow Benford's Law
        val response = BenfordResponse(
            followsBenfordsLaw = !nullHypothesisRejected,
            confidenceLevel = confidenceLevel,
            expectedDistribution = expectedDistributionOfDigits,
            actualDistribution = actualDistributionOfDigits
        )

        return response
    }

    private fun calculateExpectedDistribution(firstDigits: List<Int>): DoubleArray {
        val totalCount = firstDigits.size.toDouble()
        val expectedDistributionOfDigits = DoubleArray(9) { digit ->
            val d = digit + 1 // Convert index to actual digit (1-9)
            totalCount * log10(1.0 + 1.0 / d)
        }
        return expectedDistributionOfDigits
    }

    private fun calculateActualDistribution(firstDigits: List<Int>): LongArray {
        val actualDistributionOfDigits = LongArray(9) // Index 0 = digit 1, index 1 = digit 2, etc.
        firstDigits.forEach { digit ->
            actualDistributionOfDigits[digit - 1]++
        }
        return actualDistributionOfDigits
    }

    private fun getFirstDigits(accountBalances: List<Double>): List<Int> {
        val firstDigits = accountBalances
            .filter { it > 0 } // Only positive values
            .map { balance ->
                val firstDigit = balance.toString().first { it.isDigit() }.digitToInt()
                firstDigit
            }
            .filter { it in 1..9 } // Only digits 1-9
        return firstDigits
    }
}