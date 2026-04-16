package com.vedicmath.app.models

internal fun solveBase10Grouping(a: Int, b: Int): CalculationResult {
    val p = a - 10
    val q = b - 10
    val leftGroup = 10 + p + q
    val rightGroup = p * q
    val result = a * b

    return CalculationResult(
        methodName = "Base-10 Grouping",
        result = result.toString(),
        steps = listOf(
            "Method: Base-10 Grouping",
            "Choose base = 10",
            "$a = 10 + $p",
            "$b = 10 + $q",
            "Use: (10 + p)(10 + q) = 10(10 + p + q) + pq",
            "Left group = 10 + $p + $q = $leftGroup",
            "Right group = $p × $q = $rightGroup",
            "Grouped form = $leftGroup | $rightGroup",
            "Answer = $result"
        )
    )
}

internal fun solveSingleDigitGrouping(a: Int, b: Int): CalculationResult {
    return when {
        a in 0..9 && b >= 10 -> solveDigitGroupingCore(b, a, "1-Digit Grouping")
        b in 0..9 && a >= 10 -> solveDigitGroupingCore(a, b, "1-Digit Grouping")
        else -> overrideResult(
            name = "1-Digit Grouping",
            base = solvePositionalSplit(a, b),
            note = "No single-digit multiplier found, so positional split was used."
        )
    }
}

internal fun solveTwoDigitGrouping(a: Int, b: Int): CalculationResult {
    return when {
        a in 10..99 && b >= 100 -> solveDigitGroupingCore(b, a, "2-Digit Grouping")
        b in 10..99 && a >= 100 -> solveDigitGroupingCore(a, b, "2-Digit Grouping")
        else -> overrideResult(
            name = "2-Digit Grouping",
            base = VedicMath.solveMultiplication(a, b),
            note = "A clean two-digit grouping pattern was not available for this input."
        )
    }
}

internal fun solveDigitwiseGrouping(a: Int, b: Int, methodName: String): CalculationResult {
    val longFactor: Int
    val shortFactor: Int

    if (digitCount(a) >= digitCount(b)) {
        longFactor = a
        shortFactor = b
    } else {
        longFactor = b
        shortFactor = a
    }

    return solveDigitGroupingCore(longFactor, shortFactor, methodName)
}

internal fun solveDigitGroupingCore(
    longFactor: Int,
    shortFactor: Int,
    methodName: String
): CalculationResult {
    val digits = longFactor.toString().map { it.digitToInt() }
    val rawGroups = digits.map { it * shortFactor }
    val result = longFactor * shortFactor

    val steps = mutableListOf<String>()
    steps += "Method: $methodName"
    steps += "Long factor = $longFactor"
    steps += "Short factor = $shortFactor"

    digits.forEach { digit ->
        steps += "$digit × $shortFactor = ${digit * shortFactor}"
    }

    steps += "Grouped form = ${rawGroups.joinToString(separator = " | ")}"

    val writtenDigits = mutableListOf<Int>()
    var carry = 0

    for (i in rawGroups.indices.reversed()) {
        val total = rawGroups[i] + carry
        val writeDigit = total % 10
        carry = total / 10
        writtenDigits.add(0, writeDigit)
        steps += "$total -> write $writeDigit, carry $carry"
    }

    val answerText = buildString {
        if (carry > 0) append(carry)
        writtenDigits.forEach { append(it) }
    }

    steps += "Answer = $answerText = $result"

    return CalculationResult(
        methodName = methodName,
        result = result.toString(),
        steps = steps
    )
}

internal fun solvePositionalSplit(a: Int, b: Int): CalculationResult {
    val splitFactor: Int
    val otherFactor: Int

    if (countPlaceParts(a) <= countPlaceParts(b)) {
        splitFactor = a
        otherFactor = b
    } else {
        splitFactor = b
        otherFactor = a
    }

    val parts = decomposePlaceValues(splitFactor)
    val partials = parts.map { otherFactor * it }
    val result = a * b

    val steps = mutableListOf<String>()
    steps += "Method: Positional Split"
    steps += "Split $splitFactor into place values: ${parts.joinToString(separator = " + ")}"

    parts.forEachIndexed { index, part ->
        steps += "$otherFactor × $part = ${partials[index]}"
    }

    steps += "Add partials: ${partials.joinToString(separator = " + ")} = $result"

    return CalculationResult(
        methodName = "Positional Split",
        result = result.toString(),
        steps = steps
    )
}