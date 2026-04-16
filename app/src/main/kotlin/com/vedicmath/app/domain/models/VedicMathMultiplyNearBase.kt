package com.vedicmath.app.models

internal fun solveNearBase100Nikhilam(a: Int, b: Int): CalculationResult {
    val base = 100
    val da = base - a
    val db = base - b
    val leftRaw = a - db
    val rightRaw = da * db
    val carry = rightRaw / base
    val right = rightRaw % base
    val left = leftRaw + carry
    val result = a * b
    val rightText = right.toString().padStart(2, '0')

    return CalculationResult(
        methodName = "Near-100 Nikhilam",
        result = result.toString(),
        steps = listOf(
            "Method: Near-100 Nikhilam",
            "Base = 100",
            "$a is $da below 100",
            "$b is $db below 100",
            "Left part = $a - $db = $leftRaw",
            "Right part = $da × $db = $rightRaw",
            if (carry > 0) "Carry $carry from the right part" else "No carry needed",
            "Answer = $left | $rightText = $result"
        )
    )
}

internal fun solvePreferredNearBase(a: Int, b: Int): CalculationResult {
    return when {
        a < 100 && b < 100 && isNearBase100(a, b) -> solveNearBase100Nikhilam(a, b)
        a < 100 && b < 100 && isBase10GroupingCandidate(a, b) -> overrideResult(
            name = "Near-Base",
            base = solveBase10Grouping(a, b),
            note = "These numbers are closer to base 10 than base 100."
        )
        else -> overrideResult(
            name = "Near-Base",
            base = VedicMath.solveMultiplication(a, b),
            note = "A strong near-base pattern was not found."
        )
    }
}