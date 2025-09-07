package com.victor.dto

import kotlinx.serialization.Serializable

@Serializable
data class BenfordRequest(
    val confidenceLevel: String,
    val accountBalances: String
)
