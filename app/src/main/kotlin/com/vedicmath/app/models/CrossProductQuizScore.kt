package com.vedicmath.app.models

data class CrossProductQuizScore(
    val totalAsked: Int = 0,
    val correctAnswers: Int = 0
) {
    val incorrectAnswers: Int
        get() = totalAsked - correctAnswers

    val accuracyPercent: Int
        get() = if (totalAsked == 0) 0 else (correctAnswers * 100) / totalAsked
}