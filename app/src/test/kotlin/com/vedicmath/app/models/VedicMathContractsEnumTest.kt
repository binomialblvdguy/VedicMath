package com.vedicmath.app.models

import org.junit.Test
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue

class VedicMathContractsEnumTest {

    @Test
    fun methodChoice_isEnum_withValues() {
        val cls = Class.forName("com.vedicmath.app.models.MethodChoice")
        assertTrue("MethodChoice should be enum", cls.isEnum)
        val constants = cls.enumConstants
        assertNotNull("Enum constants should exist", constants)
        assertTrue("Enum should have values", constants!!.isNotEmpty())
    }

    @Test
    fun calculationResult_exists() {
        val cls = Class.forName("com.vedicmath.app.models.CalculationResult")
        assertNotNull("CalculationResult exists", cls)
    }
}