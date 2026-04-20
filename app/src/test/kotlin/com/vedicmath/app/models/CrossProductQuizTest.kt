package com.vedicmath.app.models

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
    fun checkAnswer_returnsFalse_forWrongAnswer() {
        val item = CrossProductQuiz.createUnitsSum5TensStep1Item(
            smallerTens = 4,
            smallerUnits = 3
        )

        assertFalse(CrossProductQuiz.checkAnswer(item, 22))
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
