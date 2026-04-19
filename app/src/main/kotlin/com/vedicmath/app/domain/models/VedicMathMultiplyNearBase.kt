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

    val steps = mutableListOf(
        "Method: Near-100 Nikhilam • Use base 100 because both numbers are close to 100",
        "$a is $da below 100, and $b is $db below 100",
        "Cross-subtract either way: $a - $db = $leftRaw and $b - $da = $leftRaw",
        "Multiply the deficits: $da × $db = $rightRaw"
    )

    if (carry > 0) {
        steps += "Carry $carry from the right part, so the left part becomes $left"
    }

    steps += "Combine the parts: $left | $rightText"
    steps += "Final answer = $result"

    return CalculationResult(
        methodName = "Near-100 Nikhilam",
        result = result.toString(),
        steps = steps
    )
}

internal fun solvePreferredNearBase(a: Int, b: Int): CalculationResult {
    return when {
        a < 100 && b < 100 && isNearBase100(a, b) -> solveNearBase100Nikhilam(a, b)
        a < 100 && b < 100 && isBase10GroupingCandidate(a, b) -> overrideResult(
            name = "Near-Base",
            base = solveBase10Grouping(a, b),
            note = "These numbers are closer to base 10 than base 100, so base-10 grouping is clearer."
        )
        else -> overrideResult(
            name = "Near-Base",
            base = VedicMath.solveMultiplication(a, b),
            note = "A strong near-base pattern was not found for these numbers."
        )
    }
}