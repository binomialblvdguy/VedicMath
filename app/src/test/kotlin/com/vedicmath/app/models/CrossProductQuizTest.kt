package com.vedicmath.app.models

data class CrossProductQuizItem(
    val leftNumber: Int,
    val rightNumber: Int,
    val expectedCrossTerm: Int,
    val typeLabel: String,
    val prompt: String,
    val explanation: String
)

object CrossProductQuiz {

    fun createUnitsSum10TensStep1Item(
        smallerTens: Int,
        smallerUnits: Int
    ): CrossProductQuizItem {
        require(smallerTens in 1..8) {
            "smallerTens must be between 1 and 8"
        }
        require(smallerUnits in 1..9) {
            "smallerUnits must be between 1 and 9"
        }

        val left = smallerTens * 10 + smallerUnits
        val right = (smallerTens + 1) * 10 + (10 - smallerUnits)
        val expectedCross = left

        return CrossProductQuizItem(
            leftNumber = left,
            rightNumber = right,
            expectedCrossTerm = expectedCross,
            typeLabel = "Units Sum 10 / Tens Differ by 1",
            prompt = "Find only the cross term for $left × $right",
            explanation = "Because the tens differ by 1 and the units add to 10, the cross term equals the smaller number: $left."
        )
    }

    fun checkAnswer(item: CrossProductQuizItem, answer: Int): Boolean {
        return item.expectedCrossTerm == answer
    }
}