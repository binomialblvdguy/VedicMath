package com.vedicmath.app.models

import kotlin.random.Random

data class CrossProductQuizScore(
    val totalAsked: Int = 0,
    val correctAnswers: Int = 0
) {
    val incorrectAnswers: Int
        get() = totalAsked - correctAnswers

    val accuracyPercent: Int
        get() = if (totalAsked == 0) 0 else (correctAnswers * 100) / totalAsked
}

class CrossProductQuizSession(
    private var score: CrossProductQuizScore = CrossProductQuizScore()
) {

    fun currentScore(): CrossProductQuizScore = score

    fun nextRandomItem(random: Random = Random.Default): CrossProductQuizItem {
        return CrossProductQuiz.createRandomItem(random)
    }

    fun recordAnswer(item: CrossProductQuizItem, answer: Int): Boolean {
        val isCorrect = CrossProductQuiz.checkAnswer(item, answer)

        score = if (isCorrect) {
            score.copy(
                totalAsked = score.totalAsked + 1,
                correctAnswers = score.correctAnswers + 1
            )
        } else {
            score.copy(
                totalAsked = score.totalAsked + 1
            )
        }

        return isCorrect
    }

    fun reset() {
        score = CrossProductQuizScore()
    }
}