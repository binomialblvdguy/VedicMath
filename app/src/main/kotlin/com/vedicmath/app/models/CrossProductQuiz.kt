package com.vedicmath.app.models

import kotlin.random.Random

object CrossProductQuiz {

    fun createUnitsSumToTensStepItem(
        smallerTens: Int,
        smallerUnits: Int
    ): CrossProductQuizItem {
        return com.vedicmath.app.domain.models.CrossProductQuiz.createUnitsSumToTensStepItem(
            smallerTens = smallerTens,
            smallerUnits = smallerUnits
        )
    }

    fun createUnitsSum10TensStep1Item(
        smallerTens: Int,
        smallerUnits: Int
    ): CrossProductQuizItem {
        return createUnitsSumToTensStepItem(
            smallerTens = smallerTens,
            smallerUnits = smallerUnits
        )
    }

    fun createUnitsSum5TensStep1Item(
        smallerTens: Int,
        smallerUnits: Int
    ): CrossProductQuizItem {
        return com.vedicmath.app.domain.models.CrossProductQuiz.createUnitsSum5TensStep1Item(
            smallerTens = smallerTens,
            smallerUnits = smallerUnits
        )
    }

    fun createUnitsSum4TensStep1Item(
        smallerTens: Int,
        smallerUnits: Int
    ): CrossProductQuizItem {
        return com.vedicmath.app.domain.models.CrossProductQuiz.createUnitsSum4TensStep1Item(
            smallerTens = smallerTens,
            smallerUnits = smallerUnits
        )
    }

    fun createUnitsSum6TensStep1Item(
        smallerTens: Int,
        smallerUnits: Int
    ): CrossProductQuizItem {
        return com.vedicmath.app.domain.models.CrossProductQuiz.createUnitsSum6TensStep1Item(
            smallerTens = smallerTens,
            smallerUnits = smallerUnits
        )
    }

    fun createUnitsSum9TensStep1Item(
        smallerTens: Int,
        smallerUnits: Int
    ): CrossProductQuizItem {
        return com.vedicmath.app.domain.models.CrossProductQuiz.createUnitsSum9TensStep1Item(
            smallerTens = smallerTens,
            smallerUnits = smallerUnits
        )
    }

    fun createUnitsSum11TensStep1Item(
        smallerTens: Int,
        smallerUnits: Int
    ): CrossProductQuizItem {
        return com.vedicmath.app.domain.models.CrossProductQuiz.createUnitsSum11TensStep1Item(
            smallerTens = smallerTens,
            smallerUnits = smallerUnits
        )
    }

    fun createUnitsSum15TensStep1Item(
        smallerTens: Int,
        smallerUnits: Int
    ): CrossProductQuizItem {
        return com.vedicmath.app.domain.models.CrossProductQuiz.createUnitsSum15TensStep1Item(
            smallerTens = smallerTens,
            smallerUnits = smallerUnits
        )
    }

    fun createUnitsSum8TensDiff2Item(
        smallerTens: Int,
        smallerUnits: Int
    ): CrossProductQuizItem {
        return com.vedicmath.app.domain.models.CrossProductQuiz.createUnitsSum8TensDiff2Item(
            smallerTens = smallerTens,
            smallerUnits = smallerUnits
        )
    }

    fun createDigits1To2RatioRightVerticalItem(
        leftBase: Int,
        rightBase: Int
    ): CrossProductQuizItem {
        return com.vedicmath.app.domain.models.CrossProductQuiz.createDigits1To2RatioRightVerticalItem(
            leftBase = leftBase,
            rightBase = rightBase
        )
    }

    fun createDigits2To1RatioLeftVerticalItem(
        leftBase: Int,
        rightBase: Int
    ): CrossProductQuizItem {
        return com.vedicmath.app.domain.models.CrossProductQuiz.createDigits2To1RatioLeftVerticalItem(
            leftBase = leftBase,
            rightBase = rightBase
        )
    }

    fun createYaavadunamStyleItem19x91(): CrossProductQuizItem {
        return com.vedicmath.app.domain.models.CrossProductQuiz.createYaavadunamStyleItem19x91()
    }

    fun createYaavadunamStyleItem29x91(): CrossProductQuizItem {
        return com.vedicmath.app.domain.models.CrossProductQuiz.createYaavadunamStyleItem29x91()
    }

    fun createRandomItem(random: Random = Random.Default): CrossProductQuizItem {
        return com.vedicmath.app.domain.models.CrossProductQuiz.createRandomItem(random)
    }

    fun checkAnswer(item: CrossProductQuizItem, answer: Int): Boolean {
        return com.vedicmath.app.domain.models.CrossProductQuiz.checkAnswer(item, answer)
    }
}