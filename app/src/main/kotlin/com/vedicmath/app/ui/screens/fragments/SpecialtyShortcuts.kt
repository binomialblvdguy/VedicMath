package com.vedicmath.app.ui.screens.fragments

import com.vedicmath.app.domain.models.VedicSolutionStep

object SpecialtyShortcuts {

    /**
     * MULTIPLICATION: UNIFIED SHIFT
     * For same tens, units sum 10-18.
     * Uses a(a+1) base + (excess * tens) shift.
     */
    fun calculateUnifiedShift(n1: Int, n2: Int): List<VedicSolutionStep> {
        val a = n1 / 10
        val u1 = n1 % 10
        val u2 = n2 % 10
        val unitSum = u1 + u2
        val excess = unitSum - 10

        val shiftValue = if (excess > 0) excess * a else 0
        val lhsBase = a * (a + 1)
        val lhsFinal = (lhsBase * 10) + shiftValue
        val rhs = u1 * u2

        return listOf(
            VedicSolutionStep("Observation", "Tens: $a. Units sum: $unitSum (Excess: $excess)"),
            VedicSolutionStep("LHS Base", "$a × (${a}+1) = $lhsBase (Treat as ${lhsBase}0)"),
            VedicSolutionStep("Shift Logic", "Excess ($excess) × Tens ($a) = +$shiftValue"),
            VedicSolutionStep("RHS", "$u1 × $u2 = $rhs"),
            VedicSolutionStep("Final", "Combine: $lhsFinal | $rhs = ${n1 * n2}")
        )
    }

    /**
     * MULTIPLICATION: POSITIONAL SPLIT (3-Parts)
     * Best for 123, 142, etc. (100 + 20 + 3 style)
     */
    fun calculatePositionalSplit(n1: Int, n2: Int): List<VedicSolutionStep> {
        val hundreds = (n2 / 100) * 100
        val tens = ((n2 % 100) / 10) * 10
        val units = n2 % 10

        val r1 = n1 * hundreds
        val r2 = n1 * tens
        val r3 = n1 * units

        return listOf(
            VedicSolutionStep("Method", "Positional Split (100 + 20 + 3)"),
            VedicSolutionStep("Step 1", "$n1 × $hundreds = $r1"),
            VedicSolutionStep("Step 2", "$n1 × $tens = $r2"),
            VedicSolutionStep("Step 3", "$n1 × $units = $r3"),
            VedicSolutionStep("Final", "Sum: $r1 + $r2 + $r3 = ${n1 * n2}")
        )
    }

    /**
     * MULTIPLICATION: PROPORTIONAL GROUPING (2-Units)
     * Best for 612 (Grouped as 6 | 12), 416, 636
     */
    fun calculateProportionalGrouping(n1: Int, n2: Int): List<VedicSolutionStep> {
        val part1 = n2 / 100
        val part2 = n2 % 100

        return listOf(
            VedicSolutionStep("Method", "Proportional Grouping ($part1 | $part2)"),
            VedicSolutionStep("Step 1", "$n1 × $part1 = ${n1 * part1} (LHS)"),
            VedicSolutionStep("Step 2", "$n1 × $part2 = ${n1 * part2} (RHS)"),
            VedicSolutionStep("Final", "Combine: ${n1 * part1}00 + ${n1 * part2} = ${n1 * n2}")
        )
    }

    /**
     * MULTIPLICATION: MANUAL RATIO (1:2 or 1:3)
     */
    fun calculateManualRatio(n1: Int, n2: Int, factor: Int): List<VedicSolutionStep> {
        val smaller = if (n1 < n2) n1 else n2
        val sq = smaller * smaller
        return listOf(
            VedicSolutionStep("Sutra", "Anurupyena (Ratio 1:$factor)"),
            VedicSolutionStep("Step 1", "Square base: $smaller² = $sq"),
            VedicSolutionStep("Step 2", "Multiply: $sq × $factor = ${sq * factor}"),
            VedicSolutionStep("Final", "Result: ${n1 * n2}")
        )
    }

    /**
     * SQUARING: THE RESTORED OBSERVATION ENGINE
     */
    fun calculateSquare(n: Int): List<VedicSolutionStep> {
        val a = n / 10
        val last = n % 10

        return when {
            // 1. ENDS IN 5: Ekadhikena
            last == 5 -> {
                val lhs = a * (a + 1)
                listOf(VedicSolutionStep("Sutra", "Ekadhikena Purvena"),
                    VedicSolutionStep("LHS", "$a × (${a+1}) = $lhs"),
                    VedicSolutionStep("Final", "$lhs" + "25"))
            }

            // 2. NEAR 100 (81-99): Yavadunam Base 100
            n in 81..99 -> {
                val def = 100 - n
                listOf(VedicSolutionStep("Sutra", "Yavadunam (Base 100)"),
                    VedicSolutionStep("LHS", "$n - $def = ${n - def}"),
                    VedicSolutionStep("RHS", "$def² = ${def * def}"),
                    VedicSolutionStep("Final", "${n * n}"))
            }

            // 3. UNIT 1: a² | 2a | 1
            last == 1 -> {
                val lhs = a * a
                val mid = 2 * a
                listOf(VedicSolutionStep("Sutra", "One-Line Unit 1: a² | 2a | 1"),
                    VedicSolutionStep("Steps", "$lhs | $mid | 1"),
                    VedicSolutionStep("Final", "${n * n}"))
            }

            // 4. UNIT 4: a(a+1) | -(2a-1) | 6
            last == 4 -> {
                val oneMore = a * (a + 1)
                val shift = (2 * a) - 1
                val baseShift = (oneMore * 10) - shift
                listOf(VedicSolutionStep("Sutra", "One-Line Unit 4: a(a+1) - (2a-1) | 6"),
                    VedicSolutionStep("Base", "$a × ${a+1} = $oneMore (as ${oneMore}0)"),
                    VedicSolutionStep("Shift", "- (2 × $a - 1) = -$shift"),
                    VedicSolutionStep("Final", "$baseShift | 6 = ${n * n}"))
            }

            // 5. UNIT 6: a(a+1) | (2a+1) | 6
            last == 6 -> {
                val lhs = a * (a + 1)
                val mid = (2 * a) + 1
                listOf(VedicSolutionStep("Sutra", "One-Line Unit 6: a(a+1) | (2a+1) | 6"),
                    VedicSolutionStep("LHS", "$a × ${a+1} = $lhs"),
                    VedicSolutionStep("Mid", "(2 × $a) + 1 = $mid"),
                    VedicSolutionStep("Final", "$lhs | $mid | 6 = ${n * n}"))
            }

            // 6. UNIT 9: (a+1)² | -2(a+1) | 1
            last == 9 -> {
                val next = a + 1
                val lhs = next * next
                val shift = 2 * next
                val baseShift = (lhs * 10) - shift
                listOf(VedicSolutionStep("Sutra", "One-Line Unit 9: (a+1)² - 2(a+1) | 1"),
                    VedicSolutionStep("Base", "$next² = $lhs (as ${lhs}0)"),
                    VedicSolutionStep("Shift", "- (2 × $next) = -$shift"),
                    VedicSolutionStep("Final", "$baseShift | 1 = ${n * n}"))
            }

            // 7. FALLBACK: Dwandwa (Duplex)
            else -> {
                val d1 = a * a
                val d12 = 2 * a * last
                val d2 = last * last
                listOf(VedicSolutionStep("Sutra", "Dwandwa (Universal)"),
                    VedicSolutionStep("Steps", "$d1 | $d12 | $d2"),
                    VedicSolutionStep("Final", "${n * n}"))
            }
        }
    }

    /**
     * CUBING: ANURUPYENA
     */
    fun calculateCube(n: Int): List<VedicSolutionStep> {
        val a = n / 10
        val b = n % 10
        val r1 = a * a * a
        val r2 = a * a * b
        val r3 = a * b * b
        val r4 = b * b * b
        return listOf(
            VedicSolutionStep("Sutra", "Anurupyena (Ratio a:b)"),
            VedicSolutionStep("Row", "$r1 | $r2 | $r3 | $r4"),
            VedicSolutionStep("Double", "Add: | ${r2*2} | ${r3*2} |"),
            VedicSolutionStep("Final", "${n * n * n}")
        )
    }

    fun calculateUniversal(n1: Int, n2: Int): List<VedicSolutionStep> =
        listOf(VedicSolutionStep("Sutra", "Urdhva Tiryak"), VedicSolutionStep("Final", "${n1 * n2}"))
}