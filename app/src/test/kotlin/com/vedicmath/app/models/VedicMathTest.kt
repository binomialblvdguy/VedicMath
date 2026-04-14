package com.vedicmath.app.models

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

class VedicMathTest {

    @Test
    fun multiply_base10Grouping_12x23() {
        val result = VedicMath.solveMultiplication(12, 23)
        assertEquals("Base-10 Grouping", result.methodName)
        assertEquals("276", result.result)
    }

    @Test
    fun multiply_verticalCrosswise_47x36() {
        val result = VedicMath.solveMultiplication(47, 36)
        assertEquals("Vertical and Crosswise", result.methodName)
        assertEquals("1692", result.result)
    }

    @Test
    fun multiply_nearBase_99x99() {
        val result = VedicMath.solveMultiplication(99, 99)
        assertEquals("Near-100 Nikhilam", result.methodName)
        assertEquals("9801", result.result)
    }

    @Test
    fun multiply_twoDigitGrouping_123x12() {
        val result = VedicMath.solveMultiplication(123, 12)
        assertEquals("2-Digit Grouping", result.methodName)
        assertEquals("1476", result.result)
    }

    @Test
    fun multiply_byOneMore_56x56() {
        val result = VedicMath.solveMultiplication(56, 56, MethodChoice.MULT_BY_ONE_MORE)
        assertEquals("By 1 More", result.methodName)
        assertEquals("3136", result.result)
    }

    @Test
    fun multiply_sum9SameTens_26x23() {
        val result = VedicMath.solveMultiplication(26, 23, MethodChoice.MULT_SUM_9)
        assertEquals("Sum 9 Same Tens", result.methodName)
        assertEquals("598", result.result)
    }

    @Test
    fun multiply_reciprocals_24x42() {
        val result = VedicMath.solveMultiplication(24, 42, MethodChoice.MULT_RECIPROCAL)
        assertEquals("Reciprocals", result.methodName)
        assertEquals("1008", result.result)
    }

    @Test
    fun square_ends1_21() {
        val result = VedicMath.solveSquare(21, MethodChoice.SQUARE_ENDS_14)
        assertEquals("Ends 1 Square", result.methodName)
        assertEquals("441", result.result)
    }

    @Test
    fun square_ends1_341() {
        val result = VedicMath.solveSquare(341, MethodChoice.SQUARE_ENDS_14)
        assertEquals("Ends 1 Square", result.methodName)
        assertEquals("116281", result.result)
    }

    @Test
    fun square_ends4_84() {
        val result = VedicMath.solveSquare(84, MethodChoice.SQUARE_ENDS_14)
        assertEquals("Ends 4 Square", result.methodName)
        assertEquals("7056", result.result)
    }

    @Test
    fun square_ends4_134() {
        val result = VedicMath.solveSquare(134, MethodChoice.SQUARE_ENDS_14)
        assertEquals("Ends 4 Square", result.methodName)
        assertEquals("17956", result.result)
    }

    @Test
    fun square_ends5_25() {
        val result = VedicMath.solveSquare(25, MethodChoice.SQUARE_ENDS_5)
        assertEquals("Ends 5 Square", result.methodName)
        assertEquals("625", result.result)
    }

    @Test
    fun square_ends6_76() {
        val result = VedicMath.solveSquare(76, MethodChoice.SQUARE_ENDS_69)
        assertEquals("Ends 6 Square", result.methodName)
        assertEquals("5776", result.result)
    }

    @Test
    fun square_ends6_126() {
        val result = VedicMath.solveSquare(126, MethodChoice.SQUARE_ENDS_69)
        assertEquals("Ends 6 Square", result.methodName)
        assertEquals("15876", result.result)
    }

    @Test
    fun square_ends9_79() {
        val result = VedicMath.solveSquare(79, MethodChoice.SQUARE_ENDS_69)
        assertEquals("Ends 9 Square", result.methodName)
        assertEquals("6241", result.result)
    }

    @Test
    fun square_ends9_129() {
        val result = VedicMath.solveSquare(129, MethodChoice.SQUARE_ENDS_69)
        assertEquals("Ends 9 Square", result.methodName)
        assertEquals("16641", result.result)
    }

    @Test
    fun cube_oneLine_12() {
        val result = VedicMath.solveCube(12, MethodChoice.CUBE_1248)
        assertEquals("One-Line 1|6|12|8", result.methodName)
        assertEquals("1728", result.result)
    }

    @Test
    fun cube_baseRow_12() {
        val result = VedicMath.solveCube(12, MethodChoice.CUBE_RATIO)
        assertEquals("Base Row 1|2|4|8", result.methodName)
        assertEquals("1728", result.result)
    }

    @Test
    fun cube_algebraic_12() {
        val result = VedicMath.solveCube(12, MethodChoice.CUBE_ALGEBRAIC)
        assertEquals("Algebraic Cube", result.methodName)
        assertEquals("1728", result.result)
    }

    @Test
    fun negative_multiplication_throws() {
        try {
            VedicMath.solveMultiplication(-1, 5)
            fail("Expected IllegalArgumentException")
        } catch (_: IllegalArgumentException) {
            assertTrue(true)
        }
    }

    @Test
    fun negative_square_throws() {
        try {
            VedicMath.solveSquare(-9)
            fail("Expected IllegalArgumentException")
        } catch (_: IllegalArgumentException) {
            assertTrue(true)
        }
    }

    @Test
    fun negative_cube_throws() {
        try {
            VedicMath.solveCube(-3)
            fail("Expected IllegalArgumentException")
        } catch (_: IllegalArgumentException) {
            assertTrue(true)
        }
    }
}