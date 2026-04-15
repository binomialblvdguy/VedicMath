package com.vedicmath.app.models

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class VedicMathContractsDirectUsageTest {

    @Test
    fun methodChoice_canBeReferencedDirectly() {
        val choice: MethodChoice = MethodChoice.AUTO
        assertEquals(MethodChoice.AUTO, choice)
    }

    @Test
    fun calculationResult_type_canBeReferencedDirectly() {
        val clazz = CalculationResult::class.java
        assertNotNull(clazz)
        assertEquals("CalculationResult", clazz.simpleName)
    }
}