package com.vedicmath.app.models

import kotlin.random.Random

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

    fun createUnitsSum5TensStep1Item(
        smallerTens: Int,
        smallerUnits: Int
    ): CrossProductQuizItem {
        require(smallerTens in 1..8) {
            "smallerTens must be between 1 and 8"
        }
        require(smallerUnits in 0..5) {
            "smallerUnits must be between 0 and 5"
        }

        val left = smallerTens * 10 + smallerUnits
        val right = (smallerTens + 1) * 10 + (5 - smallerUnits)
        val tensHalf = (smallerTens * 10) / 2
        val expectedCross = tensHalf + smallerUnits

        return CrossProductQuizItem(
            leftNumber = left,
            rightNumber = right,
            expectedCrossTerm = expectedCross,
            typeLabel = "Units Sum 5 / Tens Differ by 1",
            prompt = "Find only the cross term for $left × $right",
            explanation = "Because the tens differ by 1 and the units add to 5, take half of ${smallerTens * 10} and add $smallerUnits: $tensHalf + $smallerUnits = $expectedCross."
        )
    }

    fun createYaavadunamStyleItem19x91(): CrossProductQuizItem {
        return CrossProductQuizItem(
            leftNumber = 19,
            rightNumber = 91,
            expectedCrossTerm = 82,
            typeLabel = "Yaavadunam-Style Observation",
            prompt = "Find only the cross term for 19 × 91",
            explanation = "Deficiency from 9 is 8. One less is 7. Then 7 × 9 = 63, and 63 + 19 = 82."
        )
    }

    fun createYaavadunamStyleItem29x91(): CrossProductQuizItem {
        return CrossProductQuizItem(
            leftNumber = 29,
            rightNumber = 91,
            expectedCrossTerm = 83,
            typeLabel = "Yaavadunam-Style Observation",
            prompt = "Find only the cross term for 29 × 91",
            explanation = "Deficiency from 9 is 7. One less is 6. Then 6 × 9 = 54, and 54 + 29 = 83."
        )
    }

    fun createRandomItem(random: Random = Random.Default): CrossProductQuizItem {
        return when (random.nextInt(4)) {
            0 -> createUnitsSum10TensStep1Item(
                smallerTens = random.nextInt(1, 9),
                smallerUnits = random.nextInt(1, 10)
            )

            1 -> createUnitsSum5TensStep1Item(
                smallerTens = random.nextInt(1, 9),
                smallerUnits = random.nextInt(0, 6)
            )

            2 -> createYaavadunamStyleItem19x91()
            else -> createYaavadunamStyleItem29x91()
        }
    }

    fun checkAnswer(item: CrossProductQuizItem, answer: Int): Boolean {
        return item.expectedCrossTerm == answer
    }
}