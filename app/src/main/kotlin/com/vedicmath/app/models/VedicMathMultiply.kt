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
    val crossExplanation = buildCrossProductExplanation(
        aTens = aTens,
        aUnits = aUnits,
        bTens = bTens,
        bUnits = bUnits,
        crossRaw = crossRaw
    )
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
            "Cross: $crossExplanation, plus carry = $crossTotal, write $middleDigit carry $carryFromCross",
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

private fun buildCrossProductExplanation(
    aTens: Int,
    aUnits: Int,
    bTens: Int,
    bUnits: Int,
    crossRaw: Int
): String {
    return crossProductShortcutExplanation(
        aTens = aTens,
        aUnits = aUnits,
        bTens = bTens,
        bUnits = bUnits,
        crossRaw = crossRaw
    ) ?: "($aTens × $bUnits) + ($aUnits × $bTens) = $crossRaw"
}

private fun crossProductShortcutExplanation(
    aTens: Int,
    aUnits: Int,
    bTens: Int,
    bUnits: Int,
    crossRaw: Int
): String? {
    if (aUnits == aTens * 2 && bUnits == bTens * 2) {
        return "right-vertical shortcut: $aUnits × $bUnits = $crossRaw"
    }

    if (aTens == aUnits * 2 && bTens == bUnits * 2) {
        return "left-vertical shortcut: $aTens × $bTens = $crossRaw"
    }

    val first = TwoDigitParts(aTens, aUnits)
    val second = TwoDigitParts(bTens, bUnits)
    val (smaller, larger) = if (
        first.tens < second.tens ||
        (first.tens == second.tens && first.number <= second.number)
    ) {
        first to second
    } else {
        second to first
    }

    val tensDiff = larger.tens - smaller.tens
    val unitSum = smaller.units + larger.units

    return when {
        tensDiff == 1 && unitSum == 10 ->
            "shortcut (units sum 10, tens differ by 1): ${smaller.number} = $crossRaw"

        tensDiff == 2 && unitSum == 10 ->
            "shortcut (units sum 10, tens differ by 2): ${smaller.number} + ${smaller.units} = $crossRaw"

        tensDiff == 1 && unitSum == 5 ->
            "shortcut (units sum 5, tens differ by 1): half of ${smaller.tens * 10} + ${smaller.units} = $crossRaw"

        tensDiff == 1 && unitSum in setOf(4, 6, 9, 11) ->
            "shortcut (units sum $unitSum, tens differ by 1): $unitSum × ${smaller.tens} + ${smaller.units} = $crossRaw"

        tensDiff == 2 && unitSum == 8 ->
            "shortcut (units sum 8, tens differ by 2): 8 × ${smaller.tens} + 2 × ${smaller.units} = $crossRaw"

        else -> null
    }
}

private data class TwoDigitParts(
    val tens: Int,
    val units: Int
) {
    val number: Int get() = tens * 10 + units
}