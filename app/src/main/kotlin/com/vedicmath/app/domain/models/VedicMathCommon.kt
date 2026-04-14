package com.vedicmath.app.models

internal fun overrideResult(
    name: String,
    base: CalculationResult,
    note: String? = null
): CalculationResult {
    val newSteps = mutableListOf<String>()
    newSteps += "Method: $name"
    if (note != null) newSteps += note
    newSteps += base.steps.dropWhile { it.startsWith("Method:") }

    return CalculationResult(
        methodName = name,
        result = base.result,
        steps = newSteps
    )
}

internal fun fmtBlock(value: Int): String {
    return if (value in 0..99) value.toString().padStart(2, '0') else value.toString()
}

internal fun decomposePlaceValues(n: Int): List<Int> {
    if (n == 0) return listOf(0)

    val parts = mutableListOf<Int>()
    var place = 1
    var value = n

    while (value > 0) {
        val digit = value % 10
        if (digit != 0) parts.add(digit * place)
        value /= 10
        place *= 10
    }

    return parts.reversed()
}

internal fun countPlaceParts(n: Int): Int = decomposePlaceValues(n).size

internal fun digitCount(n: Int): Int = n.toString().length