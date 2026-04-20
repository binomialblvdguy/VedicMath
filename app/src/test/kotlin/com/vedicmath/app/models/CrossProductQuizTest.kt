package com.vedicmath.app.models

import kotlin.random.Random
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CrossProductQuizTest {

    @Test
    fun createUnitsSum10TensStep1Item_buildsExpectedExample_37x43() {
        val item = CrossProductQuiz.createUnitsSum10TensStep1Item(
            smallerTens = 3,
            smallerUnits = 7
        )

        assertEquals(37, item.leftNumber)
        assertEquals(43, item.rightNumber)
        assertEquals(37, item.expectedCrossTerm)
        assertEquals("Units Sum 10 / Tens Differ by 1", item.typeLabel)
    }

    @Test
    fun createUnitsSum10TensStep1Item_buildsExpectedExample_28x32() {
        val item = CrossProductQuiz.createUnitsSum10TensStep1Item(
            smallerTens = 2,
            smallerUnits = 8
        )

        assertEquals(28, item.leftNumber)
        assertEquals(32, item.rightNumber)
        assertEquals(28, item.expectedCrossTerm)
    }

    @Test
    fun createUnitsSum5TensStep1Item_buildsExpectedExample_43x52() {
        val item = CrossProductQuiz.createUnitsSum5TensStep1Item(
            smallerTens = 4,
            smallerUnits = 3
        )

        assertEquals(43, item.leftNumber)
        assertEquals(52, item.rightNumber)
        assertEquals(23, item.expectedCrossTerm)
        assertEquals("Units Sum 5 / Tens Differ by 1", item.typeLabel)
    }

    @Test
    fun createUnitsSum5TensStep1Item_buildsExpectedExample_33x42() {
        val item = CrossProductQuiz.createUnitsSum5TensStep1Item(
            smallerTens = 3,
            smallerUnits = 3
        )

        assertEquals(33, item.leftNumber)
        assertEquals(42, item.rightNumber)
        assertEquals(18, item.expectedCrossTerm)
    }

    @Test
    fun createYaavadunamStyleItem19x91_buildsExpectedExample() {
        val item = CrossProductQuiz.createYaavadunamStyleItem19x91()

        assertEquals(19, item.leftNumber)
        assertEquals(91, item.rightNumber)
        assertEquals(82, item.expectedCrossTerm)
        assertEquals("Yaavadunam-Style Observation", item.typeLabel)
    }

    @Test
    fun createYaavadunamStyleItem29x91_buildsExpectedExample() {
        val item = CrossProductQuiz.createYaavadunamStyleItem29x91()

        assertEquals(29, item.leftNumber)
        assertEquals(91, item.rightNumber)
        assertEquals(73, item.expectedCrossTerm)
        assertEquals("Yaavadunam-Style Observation", item.typeLabel)
    }

    @Test
    fun createRandomItem_returnsValidQuizItems() {
        val random = Random(1234)

        repeat(20) {
            val item = CrossProductQuiz.createRandomItem(random)

            assertTrue(item.leftNumber > 0)
            assertTrue(item.rightNumber > 0)
            assertTrue(item.prompt.isNotBlank())
            assertTrue(item.explanation.isNotBlank())
            assertTrue(CrossProductQuiz.checkAnswer(item, item.expectedCrossTerm))
        }
    }

    @Test
    fun createRandomItem_usesKnownRuleLabels() {
        val random = Random(5678)
        val allowedLabels = setOf(
            "Units Sum 10 / Tens Differ by 1",
            "Units Sum 5 / Tens Differ by 1",
            "Yaavadunam-Style Observation"
        )

        repeat(20) {
            val item = CrossProductQuiz.createRandomItem(random)
            assertTrue(item.typeLabel in allowedLabels)
        }
    }

    @Test
    fun checkAnswer_returnsTrue_forUnitsSum10Rule() {
        val item = CrossProductQuiz.createUnitsSum10TensStep1Item(
            smallerTens = 3,
            smallerUnits = 7
        )

        assertTrue(CrossProductQuiz.checkAnswer(item, 37))
    }

    @Test
    fun checkAnswer_returnsTrue_forUnitsSum5Rule() {
        val item = CrossProductQuiz.createUnitsSum5TensStep1Item(
            smallerTens = 4,
            smallerUnits = 3
        )

        assertTrue(CrossProductQuiz.checkAnswer(item, 23))
    }

    @Test
    fun checkAnswer_returnsTrue_forYaavadunamStyle19x91() {
        val item = CrossProductQuiz.createYaavadunamStyleItem19x91()

        assertTrue(CrossProductQuiz.checkAnswer(item, 82))
    }

    @Test
    fun checkAnswer_returnsFalse_forWrongAnswer() {
        val item = CrossProductQuiz.createYaavadunamStyleItem29x91()

        assertFalse(CrossProductQuiz.checkAnswer(item, 72))
    }

    @Test(expected = IllegalArgumentException::class)
    fun createUnitsSum10TensStep1Item_rejectsInvalidTens() {
        CrossProductQuiz.createUnitsSum10TensStep1Item(
            smallerTens = 9,
            smallerUnits = 7
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun createUnitsSum10TensStep1Item_rejectsInvalidUnits() {
        CrossProductQuiz.createUnitsSum10TensStep1Item(
            smallerTens = 3,
            smallerUnits = 0
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun createUnitsSum5TensStep1Item_rejectsInvalidUnits() {
        CrossProductQuiz.createUnitsSum5TensStep1Item(
            smallerTens = 4,
            smallerUnits = 6
        )
    }
}
