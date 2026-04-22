package com.vedicmath.app.domain.models

import com.vedicmath.app.models.CrossProductQuizItem
import com.vedicmath.app.models.CrossProductQuizScore
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

    fun currentItem(): CrossProductQuizItem? = items.getOrNull(index)

    fun newSession(size: Int = 5) {
        items.clear()
        index = 0
        score = CrossProductQuizScore()

        val usedKeys = mutableSetOf<String>()

        repeat(size) {
            val item = generateUniqueItem(usedKeys)
            items.add(item)
            usedKeys.add(item.uniqueKey())
        }
    }

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

    fun reset() {
        items.clear()
        index = 0
        score = CrossProductQuizScore()
    }

    fun isFinished(): Boolean = index >= items.size

    private fun generateUniqueItem(usedKeys: Set<String>): CrossProductQuizItem {
        repeat(50) {
            val candidate = nextRandomItem()
            if (candidate.uniqueKey() !in usedKeys) {
                return candidate
            }
        }
        return nextRandomItem()
    }

    private fun CrossProductQuizItem.uniqueKey(): String {
        return "$leftNumber|$rightNumber|$expectedCrossTerm|$typeLabel"
    }
}