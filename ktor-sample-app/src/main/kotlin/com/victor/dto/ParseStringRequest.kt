package com.victor.dto

import kotlinx.serialization.Serializable

@Serializable
data class ParseStringRequest(
    val confidenceLevel: String,
    val accountData: String
)
