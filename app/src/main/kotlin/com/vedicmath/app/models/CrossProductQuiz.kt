package com.vedicmath.app.models

import kotlin.random.Random

object CrossProductQuiz {

    fun createUnitsSum10TensStep1Item(
        smallerTens: Int,
        smallerUnits: Int
    ): CrossProductQuizItem {
        return com.vedicmath.app.domain.models.CrossProductQuiz.createUnitsSumToTensStepItem(
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
