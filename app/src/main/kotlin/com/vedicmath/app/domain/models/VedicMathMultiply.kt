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
                "$a and $b have the same tens digit = $t",
                "Units sum = $u + $v = $sumUnits",
                "Left block = $t × ${t + 1} = $left",
                "Middle block = $t × ($sumUnits - 10) = $middle",
                "Right block = $u × $v = $right",
                "One-line form = $left | ${fmtBlock(middle)} | ${fmtBlock(right)}",
                "Answer = $result"
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
                "$a and $b have the same tens digit = $t",
                "Units sum = $u + $v = 9",
                "By-1-more head = $t × ${t + 1} × 10 = $baseHead",
                "Subtract tens digit = $baseHead - $t = $left",
                "Right block = $u × $v = $right",
                "One-line form = $left | ${fmtBlock(right)}",
                "Answer = $result"
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

        val extraNote = if (t1 + t2 == 11) {
            "Since the tens digits total 11, the middle block is a multiple of 11."
        } else {
            "Middle block = units × (sum of tens digits)."
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
                "One-line form = $left | ${fmtBlock(middle)} | ${fmtBlock(right)}",
                "Answer = $result"
            )
        )
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
                "One-line form = $left | ${fmtBlock(middle)} | ${fmtBlock(right)}",
                "Answer = $result"
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

internal fun solveVerticalCrosswise(a: Int, b: Int): CalculationResult {
    val aTens = a / 10
    val aUnits = a % 10
    val bTens = b / 10
    val bUnits = b % 10

    val rightRaw = aUnits * bUnits
    val carryFromRight = rightRaw / 10
    val rightDigit = rightRaw % 10

    val crossRaw = (aTens * bUnits) + (aUnits * bTens)
    val crossTotal = crossRaw + carryFromRight
    val carryFromCross = crossTotal / 10
    val middleDigit = crossTotal % 10

    val leftTotal = (aTens * bTens) + carryFromCross
    val result = a * b

    return CalculationResult(
        methodName = "Vertical and Crosswise",
        result = result.toString(),
        steps = listOf(
            "Method: Vertical and Crosswise",
            "$a = $aTens$aUnits",
            "$b = $bTens$bUnits",
            "Right: $aUnits × $bUnits = $rightRaw, write $rightDigit carry $carryFromRight",
            "Cross: ($aTens × $bUnits) + ($aUnits × $bTens) = $crossRaw, plus carry = $crossTotal, write $middleDigit carry $carryFromCross",
            "Left: ($aTens × $bTens) + carry = ${aTens * bTens} + $carryFromCross = $leftTotal",
            "Answer = $leftTotal$middleDigit$rightDigit = $result"
        )
    )
}

internal fun solveSeriesPattern(a: Int, b: Int): CalculationResult {
    return when {
        isArithmeticDigitSeries(a) && b in 1..99 -> overrideResult(
            name = "Series Pattern",
            base = solveDigitGroupingCore(a, b, "Series Pattern"),
            note = "Detected a digit series in $a."
        )
        isArithmeticDigitSeries(b) && a in 1..99 -> overrideResult(
            name = "Series Pattern",
            base = solveDigitGroupingCore(b, a, "Series Pattern"),
            note = "Detected a digit series in $b."
        )
        else -> overrideResult(
            name = "Series Pattern",
            base = solvePositionalSplit(a, b),
            note = "No strong arithmetic digit series was detected."
        )
    }
}