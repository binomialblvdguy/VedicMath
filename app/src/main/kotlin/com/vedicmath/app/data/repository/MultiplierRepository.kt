package com.vedicmath.app.data.repository

import com.vedicmath.app.domain.models.MultiplierRatio

/**
 * MultiplierRepository: The 'Cheat Sheet' for the app.
 * Updated to use English terminology and the new flexible MultiplierRatio model.
 */
object MultiplierRepository {

    private val multiplierMap = mapOf(
        3 to MultiplierRatio(3, "¼n", "½n", "Use 1/4 and 1/2 ratios"),
        4 to MultiplierRatio(4, "¼n", "3/2n", "Use 1/4 and 3/2 ratios"),
        5 to MultiplierRatio(5, "10n/2", "0", "Standard base 10/2"),
        6 to MultiplierRatio(6, "½n", "1n", "Use 1/2 and 1 ratios"),
        7 to MultiplierRatio(7, "10n/2", "2n", "Use 10/2 and 2n"),

        // UPDATED: Sanskrit names removed for the "Base 10/20" shortcuts
        8 to MultiplierRatio(8, "n - 20", "(100-n) * 2", "Base 20 Complement Rule"),
        9 to MultiplierRatio(9, "n - 10", "100 - n", "Base 10 Complement Rule"),

        11 to MultiplierRatio(11, "1n", "0", "Identity Property"),
        12 to MultiplierRatio(12, "1n", "2n", "Double Identity")
    )

    /**
     * Fetches the specific ratio and description for a given button/multiplier.
     */
    fun getRatioForMultiplier(multiplier: Int): MultiplierRatio? {
        return multiplierMap[multiplier]
    }

    /**
     * Returns all multipliers currently supported in the Simple Multiplier module.
     */
    fun getAllMultipliers(): List<Int> = multiplierMap.keys.toList().sorted()
}