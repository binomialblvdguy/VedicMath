package com.vedicmath.app.models

import kotlin.random.Random

class CrossProductQuizSession(
    private var score: CrossProductQuizScore = CrossProductQuizScore()
) {

    private val items = mutableListOf<CrossProductQuizItem>()
    private var index = 0

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

    fun newSession(size: Int = 5) {
        items.clear()
        index = 0
        score = CrossProductQuizScore()
        repeat(size) {
            items.add(nextRandomItem())
        }
    }

    fun currentItem(): CrossProductQuizItem? = items.getOrNull(index)

    fun submitAnswer(answer: Int): Boolean {
        val item = currentItem() ?: return false
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

        index++
        return isCorrect
    }

    fun isFinished(): Boolean = index >= items.size

    fun reset() {
        items.clear()
        index = 0
        score = CrossProductQuizScore()
    }
}
