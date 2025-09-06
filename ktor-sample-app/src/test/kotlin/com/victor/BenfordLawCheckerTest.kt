package com.victor

import com.victor.math.BenfordChecker
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.lang.Math.pow

class BenfordLawCheckerTest {

    private var sut: BenfordChecker = BenfordChecker();

    @Test
    fun chiSquaredTest_shouldReturnTrueForBenfordData() {
        val benfordData = generateBenfordDoubles(1000)
        val confidenceValue = 0.05;
        val result = sut.chiSquaredTest(benfordData, confidenceValue)
        assertTrue(result, "Benford data should follow Benford's Law")
    }

    @Test
    fun chiSquaredTest_shouldReturnFalseForNonBenfordData() {
        val nonBenfordData = generateNonBenfordDoubles(1000)
        val confidenceValue = 0.05;
        val result = sut.chiSquaredTest(nonBenfordData, confidenceValue)
        assertFalse(result, "Benford data should follow Benford's Law")
    }

    private fun generateNonBenfordDoubles(size: Int): List<Double> {
        val result = mutableListOf<Double>()
        val random = kotlin.random.Random

        repeat(size) {
            // Generate a random number between 100 and 99999
            val number = random.nextInt(5, 9)
            result.add(number.toDouble())
        }

        return result
    }

    private fun generateBenfordDoubles(size: Int): List<Double> {
        // Benford's Law probabilities for digits 1-9
        val benfordProbabilities = doubleArrayOf(
            0.30103, // digit 1: log10(1 + 1/1)
            0.17609, // digit 2: log10(1 + 1/2)
            0.12494, // digit 3: log10(1 + 1/3)
            0.09691, // digit 4: log10(1 + 1/4)
            0.07918, // digit 5: log10(1 + 1/5)
            0.06695, // digit 6: log10(1 + 1/6)
            0.05799, // digit 7: log10(1 + 1/7)
            0.05115, // digit 8: log10(1 + 1/8)
            0.04576  // digit 9: log10(1 + 1/9)
        )
        
        // Create cumulative probabilities for weighted selection
        val cumulativeProbabilities = DoubleArray(9)
        cumulativeProbabilities[0] = benfordProbabilities[0]
        for (i in 1 until 9) {
            cumulativeProbabilities[i] = cumulativeProbabilities[i - 1] + benfordProbabilities[i]
        }
        
        val result = mutableListOf<Double>()
        val random = kotlin.random.Random
        
        repeat(size) {
            // Select first digit based on Benford's Law
            val randomValue = random.nextDouble()
            var firstDigit = 1
            for (i in cumulativeProbabilities.indices) {
                if (randomValue <= cumulativeProbabilities[i]) {
                    firstDigit = i + 1
                    break
                }
            }
            
            // Generate random decimal part and scale to create realistic account balance
            val decimalPart = random.nextDouble()
            val magnitude = random.nextInt(2, 6) // Creates numbers from 100s to 100,000s
            val number = (firstDigit + decimalPart) * pow(10.0, magnitude.toDouble())
            
            result.add(number)
        }
        
        return result
    }
}
