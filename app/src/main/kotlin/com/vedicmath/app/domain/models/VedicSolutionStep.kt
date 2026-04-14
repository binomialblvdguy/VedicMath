package com.vedicmath.app.domain.models

data class VedicSolutionStep(
    val title: String,
    val explanation: String,
    val value: String? = null
)