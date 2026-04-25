package com.vedicmath.app.models

internal object MultiplyExecutionEngine {
    // New minimal safe entrypoint: auto-detect observation from inputs
    internal fun solve(a: Int, b: Int): CalculationResult {
        val observation = RatioObservationEngine.observeMultiplication(a, b)
        return solve(observation, a, b)
    }

    internal fun solve(observation: MultiplyObservation, a: Int, b: Int): CalculationResult {
        return when (observation) {
            MultiplyObservation.NEAR_BASE_100 -> solveNearBase100Nikhilam(a, b)
            MultiplyObservation.SUM9_SAME_TENS -> solveSum9SameTens(a, b)
            MultiplyObservation.BY_ONE_MORE_SAME_TENS -> solveByOneMoreSameTens(a, b)
            MultiplyObservation.RECIPROCAL -> solveReciprocalDigits(a, b)
            MultiplyObservation.SAME_UNITS -> solveSameUnits(a, b)
            MultiplyObservation.BASE10_GROUPING -> solveBase10Grouping(a, b)
            MultiplyObservation.VERTICAL_CROSSWISE -> solveVerticalCrosswise(a, b)
            MultiplyObservation.DIGITWISE_GROUPING -> solveDigitwiseGrouping(a, b, "2-Digit Grouping")
            MultiplyObservation.POSITIONAL_SPLIT -> solvePositionalSplit(a, b)
        }
    }
}