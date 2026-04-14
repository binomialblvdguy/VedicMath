package com.vedicmath.app.models

import kotlin.math.abs

internal fun solveAutoMultiplication(a: Int, b: Int): CalculationResult {
    return when {
        a < 100 && b < 100 && isNearBase100(a, b) -> solveNearBase100Nikhilam(a, b)
        isSum9SameTensCandidate(a, b) -> solveSum9SameTens(a, b)
        isByOneMoreCandidate(a, b) -> solveByOneMoreSameTens(a, b)
        isReciprocalCandidate(a, b) -> solveReciprocalDigits(a, b)
        isSameUnitsCandidate(a, b) -> solveSameUnits(a, b)
        a < 100 && b < 100 && isBase10GroupingCandidate(a, b) -> solveBase10Grouping(a, b)
        a < 100 && b < 100 -> solveVerticalCrosswise(a, b)
        isDigitwiseGroupingCandidate(a, b) -> solveDigitwiseGrouping(a, b, "2-Digit Grouping")
        else -> solvePositionalSplit(a, b)
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

internal fun isNearBase100(a: Int, b: Int): Boolean {
    return a < 100 && b < 100 && abs(100 - a) <= 10 && abs(100 - b) <= 10
}

internal fun isBase10GroupingCandidate(a: Int, b: Int): Boolean {
    return a in 10..29 && b in 10..29
}

internal fun isDigitwiseGroupingCandidate(a: Int, b: Int): Boolean {
    return (digitCount(a) >= 3 && b in 10..99) || (digitCount(b) >= 3 && a in 10..99)
}

internal fun isByOneMoreCandidate(a: Int, b: Int): Boolean {
    return a in 10..99 &&
            b in 10..99 &&
            a / 10 == b / 10 &&
            (a % 10) + (b % 10) >= 10
}

internal fun isSum9SameTensCandidate(a: Int, b: Int): Boolean {
    return a in 10..99 &&
            b in 10..99 &&
            a / 10 == b / 10 &&
            (a % 10) + (b % 10) == 9
}

internal fun isSameUnitsCandidate(a: Int, b: Int): Boolean {
    return a in 10..99 && b in 10..99 && a % 10 == b % 10
}

internal fun isReciprocalCandidate(a: Int, b: Int): Boolean {
    return a in 10..99 && b in 10..99 && a / 10 == b % 10 && a % 10 == b / 10
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