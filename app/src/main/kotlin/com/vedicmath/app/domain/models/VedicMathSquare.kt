package com.vedicmath.app.models

internal fun solveEnds14Square(n: Int): CalculationResult {
    return when (n % 10) {
        1 -> solveEnds1Square(n)
        4 -> solveEnds4Square(n)
        else -> overrideResult(
            name = "Ends 1/4 Square",
            base = VedicMath.solveSquare(n),
            note = "This method is for numbers ending in 1 or 4."
        )
    }
}

internal fun solveEnds69Square(n: Int): CalculationResult {
    return when (n % 10) {
        6 -> solveEnds6Square(n)
        9 -> solveEnds9Square(n)
        else -> overrideResult(
            name = "Ends 6/9 Square",
            base = VedicMath.solveSquare(n),
            note = "This method is for numbers ending in 6 or 9."
        )
    }
}

internal fun solveEnds1Square(n: Int): CalculationResult {
    val a = n / 10
    val left = a * a
    val middle = 2 * a
    val result = n * n

    return CalculationResult(
        methodName = "Ends 1 Square",
        result = result.toString(),
        steps = listOf(
            "Method: Ends 1 Square",
            "Write $n as 10×$a + 1",
            "Square the tens part: $a² = $left",
            "Middle block = 2 × $a = $middle",
            "The final digit is 1² = 1",
            "Combine the parts: $left | $middle | 1",
            "Final answer = $result"
        )
    )
}

internal fun solveEnds4Square(n: Int): CalculationResult {
    val a = n / 10
    val frontBase = a * (a + 1) * 100
    val subtractAmount = (2 * a - 1) * 10
    val almost = frontBase - subtractAmount
    val result = n * n

    return CalculationResult(
        methodName = "Ends 4 Square",
        result = result.toString(),
        steps = listOf(
            "Method: Ends 4 Square",
            "Multiply the tens digit by the next digit and attach two zeros: $a × ${a + 1} → $frontBase",
            "Subtract ${2 * a - 1} tens: $frontBase - $subtractAmount = $almost",
            "Replace the last 0 with 6",
            "So $almost becomes $result",
            "Final answer = $result"
        )
    )
}

internal fun solveEnds5Square(n: Int): CalculationResult {
    return if (n % 10 == 5) {
        val prefix = n / 10
        val left = prefix * (prefix + 1)
        val result = n * n

        CalculationResult(
            methodName = "Ends 5 Square",
            result = result.toString(),
            steps = listOf(
                "Method: Ends 5 Square",
                "$n ends in 5, so use the 25-ending shortcut",
                "Take the prefix: $prefix",
                "Multiply the prefix by one more than itself: $prefix × ${prefix + 1} = $left",
                "Attach 25 at the end",
                "Combine the parts: $left | 25",
                "Final answer = $result"
            )
        )
    } else {
        overrideResult(
            name = "Ends 5 Square",
            base = VedicMath.solveSquare(n),
            note = "This shortcut is best for numbers ending in 5."
        )
    }
}

internal fun solveEnds6Square(n: Int): CalculationResult {
    val a = n / 10
    val frontBase = a * (a + 1) * 10
    val addAmount = 2 * (a + 1) + 1
    val prefix = frontBase + addAmount
    val result = n * n

    return CalculationResult(
        methodName = "Ends 6 Square",
        result = result.toString(),
        steps = listOf(
            "Method: Ends 6 Square",
            "Multiply the tens digit by the next digit, then by 10: $a × ${a + 1} × 10 = $frontBase",
            "Now add double the next digit plus 1: (2 × ${a + 1}) + 1 = $addAmount",
            "That gives the front part: $frontBase + $addAmount = $prefix",
            "Numbers ending in 6 finish with 6",
            "Combine the parts: $prefix | 6",
            "Final answer = $result"
        )
    )
}

internal fun solveEnds9Square(n: Int): CalculationResult {
    val a = n / 10
    val nextTen = (a + 1) * 10
    val roundedSquare = nextTen * nextTen
    val subtractAmount = 2 * nextTen
    val almost = roundedSquare - subtractAmount
    val result = n * n

    return CalculationResult(
        methodName = "Ends 9 Square",
        result = result.toString(),
        steps = listOf(
            "Method: Ends 9 Square",
            "Round $n up to the next ten: $nextTen",
            "Square that rounded number: $nextTen × $nextTen = $roundedSquare",
            "Subtract double of $nextTen: $roundedSquare - $subtractAmount = $almost",
            "Replace the last 0 with 1",
            "So $almost becomes $result",
            "Final answer = $result"
        )
    )
}

internal fun solveDuplexSquare(n: Int): CalculationResult {
    return if (n < 100) {
        val a = n / 10
        val b = n % 10

        val rightRaw = b * b
        val carryFromRight = rightRaw / 10
        val rightDigit = rightRaw % 10

        val duplex = 2 * a * b
        val middleTotal = duplex + carryFromRight
        val carryFromMiddle = middleTotal / 10
        val middleDigit = middleTotal % 10

        val leftTotal = a * a + carryFromMiddle
        val result = n * n

        CalculationResult(
            methodName = "Duplex Square",
            result = result.toString(),
            steps = listOf(
                "Method: Duplex Square",
                "Write $n as digits $a and $b",
                "Right duplex = $b² = $rightRaw, write $rightDigit and carry $carryFromRight",
                "Middle duplex = 2 × $a × $b = $duplex, then add the carry to get $middleTotal",
                "Write middle digit $middleDigit and carry $carryFromMiddle",
                "Left duplex = $a² = ${a * a}, then add the carry to get $leftTotal",
                "Combine the digits: $leftTotal$middleDigit$rightDigit",
                "Final answer = $result"
            )
        )
    } else {
        overrideResult(
            name = "Duplex Square",
            base = solveLargeSquare(n),
            note = "Using positional square for larger input."
        )
    }
}

internal fun solveLargeSquare(n: Int): CalculationResult {
    val parts = decomposePlaceValues(n)
    val result = n * n

    return CalculationResult(
        methodName = "Positional Square",
        result = result.toString(),
        steps = listOf(
            "Method: Positional Square",
            "Break $n into place values: ${parts.joinToString(separator = " + ")}",
            "Square the full number using place-value expansion",
            "Compute $n × $n = $result",
            "Final answer = $result"
        )
    )
}