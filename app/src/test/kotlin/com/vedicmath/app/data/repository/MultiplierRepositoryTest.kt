package com.vedicmath.app.data.repository

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MultiplierRepositoryTest {

    @Test
    fun getRatioForMultiplier_returnsExpectedRatio_forKnownMultiplier() {
        val ratio = MultiplierRepository.getRatioForMultiplier(3)

        assertNotNull(ratio)
        assertEquals(3, ratio!!.multiplier)
        assertEquals("¼n", ratio.primaryRatio)
        assertEquals("½n", ratio.secondaryRatio)
        assertEquals("Use 1/4 and 1/2 ratios", ratio.description)
    }

    @Test
    fun getRatioForMultiplier_returnsNull_forUnknownMultiplier() {
        val ratio = MultiplierRepository.getRatioForMultiplier(10)

        assertNull(ratio)
    }

    @Test
    fun getAllMultipliers_returnsSortedSupportedValues() {
        val multipliers = MultiplierRepository.getAllMultipliers()

        assertEquals(listOf(3, 4, 5, 6, 7, 8, 9, 11, 12), multipliers)
        assertTrue(multipliers == multipliers.sorted())
    }
}

