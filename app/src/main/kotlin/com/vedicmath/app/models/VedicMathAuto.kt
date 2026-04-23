package com.vedicmath.app.models

internal fun solveAutoMultiplication(a: Int, b: Int): CalculationResult {
    return when (RatioObservationEngine.observeMultiplication(a, b)) {
        MultiplyObservation.NEAR_BASE_100 -> solveNearBase100Nikhilam(a, b)
        MultiplyObservation.SUM9_SAME_TENS -> solveSum9SameTens(a, b)
        MultiplyObservation.BY_ONE_MORE_SAME_TENS -> solveByOneMoreSameTens(a, b)
        MultiplyObservation.RECIPROCAL -> solveReciprocalDigits(a, b)
        MultiplyObservation.SAME_UNITS -> solveSameUnits(a, b)
        MultiplyObservation.BASE10_GROUPING -> solveBase10Grouping(a, b)
        MultiplyObservation.VERTICAL_CROSSWISE -> solveVerticalCrosswise(a, b)
        MultiplyObservation.DIGITWISE_GROUPING -> solveDigitwiseGrouping(a, b, "2-Digit Grouping")
        MultiplyObservation.POSITIONAL_SPLIT -> solvePositionalSplit(a, b)
    }
}

internal fun solveAutoSquare(n: Int): CalculationResult {
    return when {
        n % 10 == 1 || n % 10 == 4 -> solveEnds14Square(n)
        n % 10 == 5 -> solveEnds5Square(n)
        n % 10 == 6 || n % 10 == 9 -> solveEnds69Square(n)
        n < 100 -> solveDuplexSquare(n)
        else -> solveLargeSquare(n)
    }
}

internal fun solveAutoCube(n: Int): CalculationResult {
    return when {
        n < 100 -> solveOneLineCube(n)
        else -> solveLargeCube(n)
    }
}

internal fun isArithmeticDigitSeries(n: Int): Boolean {
    val digits = n.toString().map { it.digitToInt() }
    if (digits.size < 3) return false

    val diff = digits[1] - digits[0]
    for (i in 1 until digits.size) {
        if (digits[i] - digits[i - 1] != diff) return false
    }

    return true
}