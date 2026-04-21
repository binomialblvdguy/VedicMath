package com.vedicmath.app.domain.models

import com.vedicmath.app.models.CrossProductQuizItem
import com.vedicmath.app.models.CrossProductQuizScore
import kotlin.random.Random

class CrossProductQuizSession(
    private var score: CrossProductQuizScore = CrossProductQuizScore()
) {

    // Internal session state
    private val items = mutableListOf<CrossProductQuizItem>()
    private var index = 0

    fun currentScore(): CrossProductQuizScore = score

    // Use domain factory to generate a random item
    fun nextRandomItem(random: Random = Random.Default): CrossProductQuizItem {
        // Assumes a central factory exists in CrossProductQuiz.kt
        return CrossProductQuiz.createRandomItem(random)
    }

    // Current item for UI
    fun currentItem(): CrossProductQuizItem? = items.getOrNull(index)

    // Start a new session with N items
    fun newSession(size: Int = 5) {
        items.clear()
        index = 0
        score = CrossProductQuizScore()
        repeat(size) {
            items.add(nextRandomItem(Random.Default))
        }
    }

    // Submit answer for current item
    fun submitAnswer(answer: Int): Boolean {
        val item = currentItem() ?: return false
        val ok = CrossProductQuiz.checkAnswer(item, answer)
        score = if (ok) {
            score.copy(totalAsked = score.totalAsked + 1, correctAnswers = score.correctAnswers + 1)
        } else {
            score.copy(totalAsked = score.totalAsked + 1)
        }
        index++
        return ok
    }

    // Reset session
    fun reset() {
        items.clear()
        index = 0
        score = CrossProductQuizScore()
    }

    // Finished?
    fun isFinished(): Boolean = index >= items.size
}