package com.vedicmath.app.domain.models

data class MethodObservation(
    val id: String,
    val shortcutName: String,
    val proof: String,
    val mentalPattern: String,
    val commonMistakes: List<String>
)