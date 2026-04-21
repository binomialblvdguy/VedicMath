package com.vedicmath.app.models

// Canonical data class for a cross-product quiz item
data class CrossProductQuizItem(
    val leftNumber: Int,
    val rightNumber: Int,
    val expectedCrossTerm: Int,
    val typeLabel: String,
    val prompt: String,
    val explanation: String
) {
    // Compatibility aliases for older code
    val left: Int
        get() = leftNumber

    val right: Int
        get() = rightNumber

    // Coaching text alias
    val ruleDescription: String
        get() = explanation
}