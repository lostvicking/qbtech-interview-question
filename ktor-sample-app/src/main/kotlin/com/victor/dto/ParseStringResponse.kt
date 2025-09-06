package com.victor.dto

import kotlinx.serialization.Serializable

@Serializable
data class ParseStringResponse(
    val followsBenfordsLaw: Boolean,
    val confidenceLevel: Double,
    val parsedNumbers: List<Double>,
    val accountData: String
)
