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
    return a < 100 && b < 100 && abs(100 - a) <= 10 && abs(100 - b) <= 10
}

internal fun isBase10GroupingCandidate(a: Int, b: Int): Boolean {
    return a in 10..29 && b in 10..29
}

internal fun isDigitwiseGroupingCandidate(a: Int, b: Int): Boolean {
    return (digitCount(a) >= 3 && b in 10..99) || (digitCount(b) >= 3 && a in 10..99)
}

internal fun isByOneMoreCandidate(a: Int, b: Int): Boolean {
    return a in 10..99 &&
            b in 10..99 &&
            a / 10 == b / 10 &&
            (a % 10) + (b % 10) >= 10
}

internal fun isSum9SameTensCandidate(a: Int, b: Int): Boolean {
    return a in 10..99 &&
            b in 10..99 &&
            a / 10 == b / 10 &&
            (a % 10) + (b % 10) == 9
}

internal fun isSameUnitsCandidate(a: Int, b: Int): Boolean {
    return a in 10..99 && b in 10..99 && a % 10 == b % 10
}

internal fun isReciprocalCandidate(a: Int, b: Int): Boolean {
    return a in 10..99 && b in 10..99 && a / 10 == b % 10 && a % 10 == b / 10
}