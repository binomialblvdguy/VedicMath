package com.vedicmath.app.models

internal fun solveAutoMultiplication(a: Int, b: Int): CalculationResult {
    // Route through engine that auto-detects observation
    return MultiplyExecutionEngine.solve(a, b)
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