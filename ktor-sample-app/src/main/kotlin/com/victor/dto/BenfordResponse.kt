package com.victor.dto

import kotlinx.serialization.Serializable

@Serializable
data class BenfordResponse(
    val followsBenfordsLaw: Boolean,
    val confidenceLevel: Double,
    val expectedDistribution: DoubleArray,
    val actualDistribution: LongArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BenfordResponse

        if (followsBenfordsLaw != other.followsBenfordsLaw) return false
        if (confidenceLevel != other.confidenceLevel) return false
        if (!expectedDistribution.contentEquals(other.expectedDistribution)) return false
        if (!actualDistribution.contentEquals(other.actualDistribution)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = followsBenfordsLaw.hashCode()
        result = 31 * result + confidenceLevel.hashCode()
        result = 31 * result + expectedDistribution.contentHashCode()
        result = 31 * result + actualDistribution.contentHashCode()
        return result
    }
}
