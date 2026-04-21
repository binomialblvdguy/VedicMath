package com.vedicmath.app.crossproduct.advanced

import kotlin.random.Random

// A simple data class to represent a 2-digit "AB × CD" style problem
data class AdvancedCrossProductQuestion(
    val left: Int,  // AB
    val right: Int  // CD
)

sealed class AdvancedCrossProductRule(
    val id: String,
    val name: String,
    val description: String
) {
    abstract fun generate(): AdvancedCrossProductQuestion

    // New rule: Units sum to 8; tens digits differ by 2
    object UnitsSumTo8_TensDiff2 : AdvancedCrossProductRule(
        "u8_t2",
        "Units sum to 8 / tens differ by 2",
        "B + D = 8 and |A - C| = 2"
    ) {
        override fun generate(): AdvancedCrossProductQuestion {
            // Choose AB with A in 1..9, ensure C chosen to differ by 2
            val a = Random.nextInt(1, 10)
            val c = when {
                a <= 2 -> a + 2
                a >= 8 -> a - 2
                else -> if (Random.nextBoolean()) a + 2 else a - 2
            }

            // Choose B such that D = 8 - B stays a digit (0..9)
            val b = Random.nextInt(0, 8)
            val d = 8 - b

            val left = 10 * a + b
            val right = 10 * c + d
            return AdvancedCrossProductQuestion(left, right)
        }
    }
}

object AdvancedCrossProductRuleRegistry {
    val allRules: List<AdvancedCrossProductRule> = listOf(
        AdvancedCrossProductRule.UnitsSumTo8_TensDiff2
    )
}
