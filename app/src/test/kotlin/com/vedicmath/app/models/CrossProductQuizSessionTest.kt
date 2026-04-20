package com.vedicmath.app.models

import kotlin.random.Random
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CrossProductQuizSessionTest {

    @Test
    fun currentScore_startsAtZero() {
        val session = CrossProductQuizSession()

        val score = session.currentScore()

        assertEquals(0, score.totalAsked)
        assertEquals(0, score.correctAnswers)
        assertEquals(0, score.incorrectAnswers)
        assertEquals(0, score.accuracyPercent)
    }

    @Test
    fun recordAnswer_updatesScore_forCorrectAnswer() {
        val session = CrossProductQuizSession()
        val item = CrossProductQuiz.createUnitsSum10TensStep1Item(
            smallerTens = 3,
            smallerUnits = 7
        )

        val wasCorrect = session.recordAnswer(item, 37)
        val score = session.currentScore()

        assertTrue(wasCorrect)
        assertEquals(1, score.totalAsked)
        assertEquals(1, score.correctAnswers)
        assertEquals(0, score.incorrectAnswers)
        assertEquals(100, score.accuracyPercent)
    }

    @Test
    fun recordAnswer_updatesScore_forWrongAnswer() {
        val session = CrossProductQuizSession()
        val item = CrossProductQuiz.createUnitsSum5TensStep1Item(
            smallerTens = 4,
            smallerUnits = 3
        )

        val wasCorrect = session.recordAnswer(item, 22)
        val score = session.currentScore()

        assertFalse(wasCorrect)
        assertEquals(1, score.totalAsked)
        assertEquals(0, score.correctAnswers)
        assertEquals(1, score.incorrectAnswers)
        assertEquals(0, score.accuracyPercent)
    }

    @Test
    fun recordAnswer_accumulatesMultipleAnswers() {
        val session = CrossProductQuizSession()

        val item1 = CrossProductQuiz.createUnitsSum10TensStep1Item(
            smallerTens = 3,
            smallerUnits = 7
        )
        val item2 = CrossProductQuiz.createUnitsSum5TensStep1Item(
            smallerTens = 4,
            smallerUnits = 3
        )
        val item3 = CrossProductQuiz.createYaavadunamStyleItem19x91()

        session.recordAnswer(item1, 37)
        session.recordAnswer(item2, 22)
        session.recordAnswer(item3, 82)

        val score = session.currentScore()

        assertEquals(3, score.totalAsked)
        assertEquals(2, score.correctAnswers)
        assertEquals(1, score.incorrectAnswers)
        assertEquals(66, score.accuracyPercent)
    }

    @Test
    fun nextRandomItem_returnsUsableQuizItem() {
        val session = CrossProductQuizSession()
        val item = session.nextRandomItem(Random(1234))

        assertTrue(item.leftNumber > 0)
        assertTrue(item.rightNumber > 0)
        assertTrue(item.expectedCrossTerm > 0)
        assertTrue(item.prompt.isNotBlank())
        assertTrue(item.explanation.isNotBlank())
    }

    @Test
    fun reset_clearsScore() {
        val session = CrossProductQuizSession()
        val item = CrossProductQuiz.createYaavadunamStyleItem29x91()

        session.recordAnswer(item, 73)
        session.reset()

        val score = session.currentScore()

        assertEquals(0, score.totalAsked)
        assertEquals(0, score.correctAnswers)
        assertEquals(0, score.incorrectAnswers)
        assertEquals(0, score.accuracyPercent)
    }
}