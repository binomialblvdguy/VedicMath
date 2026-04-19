package com.vedicmath.app.models

internal fun solveByOneMoreSameTens(a: Int, b: Int): CalculationResult {
    return if (isByOneMoreCandidate(a, b)) {
        val t = a / 10
        val u = a % 10
        val v = b % 10
        val sumUnits = u + v
        val excess = sumUnits - 10

        val left = t * (t + 1)
        val middle = t * excess
        val right = u * v
        val result = a * b

        CalculationResult(
            methodName = "By 1 More",
            result = result.toString(),
            steps = listOf(
                "Method: By 1 More",
                "$a and $b share the same tens digit: $t",
                "Add the units digits: $u + $v = $sumUnits",
                "Since the units total is above 10, the extra part is $sumUnits - 10 = $excess",
                "Left block = $t × ${t + 1} = $left",
                "Middle block = $t × $excess = $middle",
                "Right block = $u × $v = $right",
                "Combine the blocks: $left | ${fmtBlock(middle)} | ${fmtBlock(right)}",
                "Final answer = $result"
            )
        )
    } else {
        overrideResult(
            name = "By 1 More",
            base = VedicMath.solveMultiplication(a, b),
            note = "This works when the tens digits match and the units total 10 or more."
        )
    }
}

internal fun solveSum9SameTens(a: Int, b: Int): CalculationResult {
    return if (isSum9SameTensCandidate(a, b)) {
        val t = a / 10
        val u = a % 10
        val v = b % 10

        val baseHead = t * (t + 1) * 10
        val left = baseHead - t
        val right = u * v
        val result = a * b

        CalculationResult(
            methodName = "Sum 9 Same Tens",
            result = result.toString(),
            steps = listOf(
                "Method: Sum 9 Same Tens",
                "$a and $b share the same tens digit: $t",
                "Add the units digits: $u + $v = 9",
                "Start with the by-1-more head: $t × ${t + 1} × 10 = $baseHead",
                "Subtract the tens digit: $baseHead - $t = $left",
                "Right block = $u × $v = $right",
                "Combine the blocks: $left | ${fmtBlock(right)}",
                "Final answer = $result"
            )
        )
    } else {
        overrideResult(
            name = "Sum 9 Same Tens",
            base = VedicMath.solveMultiplication(a, b),
            note = "This works when the tens digits match and the units total exactly 9."
        )
    }
}

internal fun solveSameUnits(a: Int, b: Int): CalculationResult {
    return if (isSameUnitsCandidate(a, b)) {
        val t1 = a / 10
        val t2 = b / 10
        val u = a % 10

        val left = t1 * t2
        val middle = u * (t1 + t2)
        val right = u * u
        val result = a * b

        if (u == 5) {
            val tensSum = t1 + t2
            val normalizedLeft = left + (middle / 10)
            val normalizedSuffix = if (middle % 10 == 0) 25 else 75
            val parityWord = if (tensSum % 2 == 0) "even" else "odd"

            CalculationResult(
                methodName = "Same Units",
                result = result.toString(),
                steps = listOf(
                    "Same Units / Both end in 5",
                    "Prefix = $t1 × $t2 = $left",
                    "Middle rule for ...5 × ...5:\n5 × ($t1 + $t2) = 5 × $tensSum = $middle, so the ending becomes 25 or 75",
                    "Here the middle block is $middle",
                    "The tens sum $tensSum is $parityWord, so the end block is $normalizedSuffix",
                    "Normalize the form: $left + ${middle / 10} | $normalizedSuffix = $normalizedLeft | $normalizedSuffix",
                    "Final answer = $result"
                )
            )
        } else {
            val extraNote = if (t1 + t2 == 11) {
                "The tens digits add to 11, so the middle block follows a nice 11-pattern."
            } else {
                "The middle block comes from units × (sum of the tens digits)."
            }

            CalculationResult(
                methodName = "Same Units",
                result = result.toString(),
                steps = listOf(
                    "Method: Same Units",
                    "$a and $b both end in $u",
                    "Left block = $t1 × $t2 = $left",
                    "Middle block = $u × (${t1 + t2}) = $middle",
                    "Right block = $u × $u = $right",
                    extraNote,
                    "Combine the blocks: $left | ${fmtBlock(middle)} | ${fmtBlock(right)}",
                    "Final answer = $result"
                )
            )
        }
    } else {
        overrideResult(
            name = "Same Units",
            base = VedicMath.solveMultiplication(a, b),
            note = "This works when both numbers end in the same units digit."
        )
    }
}

internal fun solveReciprocalDigits(a: Int, b: Int): CalculationResult {
    return if (isReciprocalCandidate(a, b)) {
        val x = a / 10
        val y = a % 10

        val left = x * y
        val middle = (x * x) + (y * y)
        val right = x * y
        val result = a * b

        CalculationResult(
            methodName = "Reciprocals",
            result = result.toString(),
            steps = listOf(
                "Method: Reciprocals",
                "$a and $b are reverse-digit pairs",
                "Left block = $x × $y = $left",
                "Middle block = $x² + $y² = ${x * x} + ${y * y} = $middle",
                "Right block = $x × $y = $right",
                "Combine the blocks: $left | ${fmtBlock(middle)} | ${fmtBlock(right)}",
                "Final answer = $result"
            )
        )
    } else {
        overrideResult(
            name = "Reciprocals",
            base = VedicMath.solveMultiplication(a, b),
            note = "This works for reverse-digit pairs such as 24 × 42."
        )
    }
}