package com.vedicmath.app.models

import kotlin.math.abs

internal enum class MultiplyObservation {
    NEAR_BASE_100,
    SUM9_SAME_TENS,
    BY_ONE_MORE_SAME_TENS,
    RECIPROCAL,
    SAME_UNITS,
    BASE10_GROUPING,
    VERTICAL_CROSSWISE,
    DIGITWISE_GROUPING,
    POSITIONAL_SPLIT
}

internal object RatioObservationEngine {
    internal fun observeMultiplication(a: Int, b: Int): MultiplyObservation {
        // Tightened handling: route negative inputs to a safe, generic path
        if (a < 0 || b < 0) {
            return MultiplyObservation.POSITIONAL_SPLIT
        }

        return when {
            isNearBase100(a, b) -> MultiplyObservation.NEAR_BASE_100
            isSum9SameTensCandidate(a, b) -> MultiplyObservation.SUM9_SAME_TENS
            isByOneMoreCandidate(a, b) -> MultiplyObservation.BY_ONE_MORE_SAME_TENS
            isReciprocalCandidate(a, b) -> MultiplyObservation.RECIPROCAL
            isSameUnitsCandidate(a, b) -> MultiplyObservation.SAME_UNITS
            a < 100 && b < 100 && isBase10GroupingCandidate(a, b) -> MultiplyObservation.BASE10_GROUPING
            a < 100 && b < 100 -> MultiplyObservation.VERTICAL_CROSSWISE
            isDigitwiseGroupingCandidate(a, b) -> MultiplyObservation.DIGITWISE_GROUPING
            else -> MultiplyObservation.POSITIONAL_SPLIT
        }
    }
}

internal fun isNearBase100(a: Int, b: Int): Boolean {
    val A = abs(a)
    val B = abs(b)
    return A <= 100 && B <= 100 && abs(100 - A) <= 10 && abs(100 - B) <= 10
}

internal fun isBase10GroupingCandidate(a: Int, b: Int): Boolean {
    val A = abs(a)
    val B = abs(b)
    return A in 10..29 && B in 10..29
}

internal fun isDigitwiseGroupingCandidate(a: Int, b: Int): Boolean {
    val A = abs(a)
    val B = abs(b)
    return (digitCount(A) >= 3 && B in 10..99) || (digitCount(B) >= 3 && A in 10..99)
}

internal fun isByOneMoreCandidate(a: Int, b: Int): Boolean {
    val A = abs(a)
    val B = abs(b)
    return A in 10..99 &&
            B in 10..99 &&
            A / 10 == B / 10 &&
            (A % 10) + (B % 10) >= 10
}

internal fun isSum9SameTensCandidate(a: Int, b: Int): Boolean {
    val A = abs(a)
    val B = abs(b)
    return A in 10..99 &&
            B in 10..99 &&
            A / 10 == B / 10 &&
            (A % 10) + (B % 10) == 9
}

internal fun isSameUnitsCandidate(a: Int, b: Int): Boolean {
    val A = abs(a)
    val B = abs(b)
    return A in 10..99 && B in 10..99 && A % 10 == B % 10
}

internal fun isReciprocalCandidate(a: Int, b: Int): Boolean {
    val A = abs(a)
    val B = abs(b)
    return A in 10..99 && B in 10..99 && A / 10 == B % 10 && A % 10 == B / 10
}