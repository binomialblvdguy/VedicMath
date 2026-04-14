package com.vedicmath.app.models

object VedicMath {

    fun solveMultiplication(
        a: Int,
        b: Int,
        method: MethodChoice = MethodChoice.AUTO
    ): CalculationResult {
        require(a >= 0 && b >= 0) { "Only non-negative numbers are supported." }

        return when (method) {
            MethodChoice.AUTO -> solveAutoMultiplication(a, b)
            MethodChoice.MULT_BY_ONE_MORE -> solveByOneMoreSameTens(a, b)
            MethodChoice.MULT_SUM_9 -> solveSum9SameTens(a, b)
            MethodChoice.MULT_SAME_UNITS -> solveSameUnits(a, b)
            MethodChoice.MULT_RECIPROCAL -> solveReciprocalDigits(a, b)
            MethodChoice.MULT_GROUP_1 -> solveSingleDigitGrouping(a, b)
            MethodChoice.MULT_GROUP_2 -> solveTwoDigitGrouping(a, b)
            MethodChoice.MULT_VERTICAL -> {
                if (a < 100 && b < 100) {
                    solveVerticalCrosswise(a, b)
                } else {
                    overrideResult(
                        name = "Vertical and Crosswise",
                        base = solvePositionalSplit(a, b),
                        note = "Using positional split because vertical-and-crosswise is currently implemented cleanly for two-digit multiplication."
                    )
                }
            }
            MethodChoice.MULT_NEAR_BASE -> solvePreferredNearBase(a, b)
            MethodChoice.MULT_SERIES -> solveSeriesPattern(a, b)
            else -> solveAutoMultiplication(a, b)
        }
    }

    fun solveSquare(
        n: Int,
        method: MethodChoice = MethodChoice.AUTO
    ): CalculationResult {
        require(n >= 0) { "Only non-negative numbers are supported." }

        return when (method) {
            MethodChoice.AUTO -> solveAutoSquare(n)
            MethodChoice.SQUARE_DUPLEX -> solveDuplexSquare(n)
            MethodChoice.SQUARE_ENDS_14 -> solveEnds14Square(n)
            MethodChoice.SQUARE_ENDS_5 -> solveEnds5Square(n)
            MethodChoice.SQUARE_ENDS_69 -> solveEnds69Square(n)
            else -> solveAutoSquare(n)
        }
    }

    fun solveCube(
        n: Int,
        method: MethodChoice = MethodChoice.AUTO
    ): CalculationResult {
        require(n >= 0) { "Only non-negative numbers are supported." }

        return when (method) {
            MethodChoice.AUTO -> solveAutoCube(n)
            MethodChoice.CUBE_1248 -> solveOneLineCube(n)
            MethodChoice.CUBE_RATIO -> solveBaseRowCube(n)
            MethodChoice.CUBE_ALGEBRAIC -> solveAlgebraicCube(n)
            else -> solveAutoCube(n)
        }
    }

    fun multiplyTwoDigitsNikhilam(a: Int, b: Int): Int {
        return solveMultiplication(a, b).result.toInt()
    }

    fun multiplyTwoDigitsNikhilamSafe(a: Int, b: Int): Int? {
        if (a < 0 || b < 0) return null
        return solveMultiplication(a, b).result.toInt()
    }

    fun stepsForTwoDigitsNikhilam(a: Int, b: Int): List<String> {
        return solveMultiplication(a, b).steps
    }

    fun methodLabelForTwoDigits(a: Int, b: Int): String {
        return solveMultiplication(a, b).methodName
    }
}