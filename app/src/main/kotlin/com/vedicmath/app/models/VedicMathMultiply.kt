package com.vedicmath.app.models

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