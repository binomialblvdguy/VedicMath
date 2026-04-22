package com.vedicmath.app.domain.models

import com.vedicmath.app.models.CrossProductQuizItem
import kotlin.random.Random

object CrossProductQuiz {

    private fun createFixedUnitSumTensDiffItem(
        unitSum: Int,
        tensDiff: Int,
        smallerTens: Int,
        smallerUnits: Int,
        typeLabel: String,
        explanation: String
    ): CrossProductQuizItem {
        require(tensDiff in 1..8) {
            "tensDiff must be between 1 and 8"
        }

        require(smallerTens in 1..(9 - tensDiff)) {
            "smallerTens must be between 1 and ${9 - tensDiff}"
        }

        val minUnits = maxOf(0, unitSum - 9)
        val maxUnits = minOf(9, unitSum)

        require(smallerUnits in minUnits..maxUnits) {
            "smallerUnits must be between $minUnits and $maxUnits"
        }

        val left = smallerTens * 10 + smallerUnits
        val rightUnits = unitSum - smallerUnits
        val right = (smallerTens + tensDiff) * 10 + rightUnits
        val expectedCross = unitSum * smallerTens + tensDiff * smallerUnits

        return CrossProductQuizItem(
            leftNumber = left,
            rightNumber = right,
            expectedCrossTerm = expectedCross,
            typeLabel = typeLabel,
            prompt = "Find only the cross term for $left × $right",
            explanation = explanation
        )
    }

    fun createUnitsSumToTensStepItem(
        smallerTens: Int,
        smallerUnits: Int
    ): CrossProductQuizItem {
        val left = smallerTens * 10 + smallerUnits

        return createFixedUnitSumTensDiffItem(
            unitSum = 10,
            tensDiff = 1,
            smallerTens = smallerTens,
            smallerUnits = smallerUnits,
            typeLabel = "Units Sum 10 / Tens Differ by 1",
            explanation = "Because the tens differ by 1 and the units add to 10, the cross term equals the smaller number: $left."
        )
    }

    fun createUnitsSum5TensStep1Item(
        smallerTens: Int,
        smallerUnits: Int
    ): CrossProductQuizItem {
        val tensHalf = (smallerTens * 10) / 2
        val expectedCross = 5 * smallerTens + smallerUnits

        return createFixedUnitSumTensDiffItem(
            unitSum = 5,
            tensDiff = 1,
            smallerTens = smallerTens,
            smallerUnits = smallerUnits,
            typeLabel = "Units Sum 5 / Tens Differ by 1",
            explanation = "Because the tens differ by 1 and the units add to 5, take half of ${smallerTens * 10} and add $smallerUnits: $tensHalf + $smallerUnits = $expectedCross."
        )
    }

    fun createUnitsSum4TensStep1Item(
        smallerTens: Int,
        smallerUnits: Int
    ): CrossProductQuizItem {
        val expectedCross = 4 * smallerTens + smallerUnits

        return createFixedUnitSumTensDiffItem(
            unitSum = 4,
            tensDiff = 1,
            smallerTens = smallerTens,
            smallerUnits = smallerUnits,
            typeLabel = "Units Sum 4 / Tens Differ by 1",
            explanation = "Because the tens differ by 1 and the units add to 4, the cross term is 4 × $smallerTens + $smallerUnits = $expectedCross."
        )
    }

    fun createUnitsSum6TensStep1Item(
        smallerTens: Int,
        smallerUnits: Int
    ): CrossProductQuizItem {
        val expectedCross = 6 * smallerTens + smallerUnits

        return createFixedUnitSumTensDiffItem(
            unitSum = 6,
            tensDiff = 1,
            smallerTens = smallerTens,
            smallerUnits = smallerUnits,
            typeLabel = "Units Sum 6 / Tens Differ by 1",
            explanation = "Because the tens differ by 1 and the units add to 6, the cross term is 6 × $smallerTens + $smallerUnits = $expectedCross."
        )
    }

    fun createUnitsSum9TensStep1Item(
        smallerTens: Int,
        smallerUnits: Int
    ): CrossProductQuizItem {
        val expectedCross = 9 * smallerTens + smallerUnits

        return createFixedUnitSumTensDiffItem(
            unitSum = 9,
            tensDiff = 1,
            smallerTens = smallerTens,
            smallerUnits = smallerUnits,
            typeLabel = "Units Sum 9 / Tens Differ by 1",
            explanation = "Because the tens differ by 1 and the units add to 9, the cross term is 9 × $smallerTens + $smallerUnits = $expectedCross."
        )
    }

    fun createUnitsSum11TensStep1Item(
        smallerTens: Int,
        smallerUnits: Int
    ): CrossProductQuizItem {
        val expectedCross = 11 * smallerTens + smallerUnits

        return createFixedUnitSumTensDiffItem(
            unitSum = 11,
            tensDiff = 1,
            smallerTens = smallerTens,
            smallerUnits = smallerUnits,
            typeLabel = "Units Sum 11 / Tens Differ by 1",
            explanation = "Because the tens differ by 1 and the units add to 11, the cross term is 11 × $smallerTens + $smallerUnits = $expectedCross."
        )
    }

    fun createUnitsSum8TensDiff2Item(
        smallerTens: Int,
        smallerUnits: Int
    ): CrossProductQuizItem {
        val expectedCross = 8 * smallerTens + 2 * smallerUnits

        return createFixedUnitSumTensDiffItem(
            unitSum = 8,
            tensDiff = 2,
            smallerTens = smallerTens,
            smallerUnits = smallerUnits,
            typeLabel = "Units Sum 8 / Tens Differ by 2",
            explanation = "Because the tens differ by 2 and the units add to 8, the cross term is 8 × $smallerTens + 2 × $smallerUnits = $expectedCross."
        )
    }

    fun createDigits1To2RatioRightVerticalItem(
        leftBase: Int,
        rightBase: Int
    ): CrossProductQuizItem {
        require(leftBase in 1..4) {
            "leftBase must be between 1 and 4"
        }
        require(rightBase in 1..4) {
            "rightBase must be between 1 and 4"
        }

        val leftTens = leftBase
        val leftUnits = leftBase * 2
        val rightTens = rightBase
        val rightUnits = rightBase * 2

        val left = leftTens * 10 + leftUnits
        val right = rightTens * 10 + rightUnits
        val expectedCross = leftUnits * rightUnits

        return CrossProductQuizItem(
            leftNumber = left,
            rightNumber = right,
            expectedCrossTerm = expectedCross,
            typeLabel = "Digits in 1:2 Ratio / Cross Product = Right Vertical",
            prompt = "Find only the cross term for $left × $right",
            explanation = "Because both numbers have digits in a 1:2 ratio, the cross product equals the right vertical product: $leftUnits × $rightUnits = $expectedCross."
        )
    }

    fun createDigits2To1RatioLeftVerticalItem(
        leftBase: Int,
        rightBase: Int
    ): CrossProductQuizItem {
        require(leftBase in 1..4) {
            "leftBase must be between 1 and 4"
        }
        require(rightBase in 1..4) {
            "rightBase must be between 1 and 4"
        }

        val leftTens = leftBase * 2
        val leftUnits = leftBase
        val rightTens = rightBase * 2
        val rightUnits = rightBase

        val left = leftTens * 10 + leftUnits
        val right = rightTens * 10 + rightUnits
        val expectedCross = leftTens * rightTens

        return CrossProductQuizItem(
            leftNumber = left,
            rightNumber = right,
            expectedCrossTerm = expectedCross,
            typeLabel = "Digits in 2:1 Ratio / Cross Product = Left Vertical",
            prompt = "Find only the cross term for $left × $right",
            explanation = "Because both numbers have digits in a 2:1 ratio, the cross product equals the left vertical product: $leftTens × $rightTens = $expectedCross."
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
        return when (random.nextInt(11)) {
            0 -> createUnitsSumToTensStepItem(
                smallerTens = random.nextInt(1, 9),
                smallerUnits = random.nextInt(1, 10)
            )
            1 -> createUnitsSum5TensStep1Item(
                smallerTens = random.nextInt(1, 9),
                smallerUnits = random.nextInt(0, 6)
            )
            2 -> createUnitsSum8TensDiff2Item(
                smallerTens = random.nextInt(1, 8),
                smallerUnits = random.nextInt(0, 9)
            )
            3 -> createUnitsSum4TensStep1Item(
                smallerTens = random.nextInt(1, 9),
                smallerUnits = random.nextInt(0, 5)
            )
            4 -> createUnitsSum6TensStep1Item(
                smallerTens = random.nextInt(1, 9),
                smallerUnits = random.nextInt(0, 7)
            )
            5 -> createUnitsSum9TensStep1Item(
                smallerTens = random.nextInt(1, 9),
                smallerUnits = random.nextInt(0, 10)
            )
            6 -> createUnitsSum11TensStep1Item(
                smallerTens = random.nextInt(1, 9),
                smallerUnits = random.nextInt(2, 10)
            )
            7 -> createDigits1To2RatioRightVerticalItem(
                leftBase = random.nextInt(1, 5),
                rightBase = random.nextInt(1, 5)
            )
            8 -> createDigits2To1RatioLeftVerticalItem(
                leftBase = random.nextInt(1, 5),
                rightBase = random.nextInt(1, 5)
            )
            9 -> createYaavadunamStyleItem19x91()
            else -> createYaavadunamStyleItem29x91()
        }
    }

    fun checkAnswer(item: CrossProductQuizItem, answer: Int): Boolean {
        return item.expectedCrossTerm == answer
    }
}