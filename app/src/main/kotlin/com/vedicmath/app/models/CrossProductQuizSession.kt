package com.vedicmath.app.models

import kotlin.random.Random

class CrossProductQuizSession(
    score: CrossProductQuizScore = CrossProductQuizScore()
) {
    private val delegate = com.vedicmath.app.domain.models.CrossProductQuizSession(score)

    fun currentScore(): CrossProductQuizScore = delegate.currentScore()

    fun nextRandomItem(random: Random = Random.Default): CrossProductQuizItem {
        return delegate.nextRandomItem(random)
    }

    fun recordAnswer(item: CrossProductQuizItem, answer: Int): Boolean {
        return delegate.recordAnswer(item, answer)
    }

    fun newSession(size: Int = 5) {
        delegate.newSession(size)
    }

    fun currentItem(): CrossProductQuizItem? = delegate.currentItem()

    fun submitAnswer(answer: Int): Boolean {
        return delegate.submitAnswer(answer)
    }

    fun isFinished(): Boolean = delegate.isFinished()

    fun reset() {
        delegate.reset()
    }
}