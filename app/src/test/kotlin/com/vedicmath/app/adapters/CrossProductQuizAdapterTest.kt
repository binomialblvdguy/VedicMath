package com.vedicmath.app.adapters

import com.vedicmath.app.models.CrossProductQuizItem
import org.junit.Assert.assertEquals
import org.junit.Test

class CrossProductQuizAdapterTest {

    @Test
    fun testToModelIdentityFields() {
        val input = CrossProductQuizItem(
            leftNumber = 1,
            rightNumber = 2,
            expectedCrossTerm = 3,
            typeLabel = "Test",
            prompt = "Find cross term",
            explanation = "explanation"
        )

        val result = CrossProductQuizAdapter.toModel(input)

        assertEquals(input.leftNumber, result.leftNumber)
        assertEquals(input.rightNumber, result.rightNumber)
        assertEquals(input.expectedCrossTerm, result.expectedCrossTerm)
        assertEquals(input.typeLabel, result.typeLabel)
        assertEquals(input.prompt, result.prompt)
        assertEquals(input.explanation, result.explanation)
    }

    @Test
    fun testToModelListPreservesFields() {
        val a = CrossProductQuizItem(1, 2, 3, "t1", "p1", "e1")
        val b = CrossProductQuizItem(4, 5, 9, "t2", "p2", "e2")
        val inputList = listOf(a, b)

        val mapped = CrossProductQuizAdapter.toModelList(inputList)

        assertEquals(inputList.size, mapped.size)
        for (i in inputList.indices) {
            assertEquals(inputList[i].leftNumber, mapped[i].leftNumber)
            assertEquals(inputList[i].rightNumber, mapped[i].rightNumber)
            assertEquals(inputList[i].expectedCrossTerm, mapped[i].expectedCrossTerm)
            assertEquals(inputList[i].typeLabel, mapped[i].typeLabel)
            assertEquals(inputList[i].prompt, mapped[i].prompt)
            assertEquals(inputList[i].explanation, mapped[i].explanation)
        }
    }
}
