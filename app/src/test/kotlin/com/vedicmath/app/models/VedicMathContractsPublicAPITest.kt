package com.vedicmath.app.models

import org.junit.Test
import org.junit.Assert.assertTrue
import java.lang.reflect.Modifier

class VedicMathContractsPublicAPITest {

    @Test
    fun methodChoice_isPublic() {
        val cls = Class.forName("com.vedicmath.app.models.MethodChoice")
        assertTrue("MethodChoice should be public", Modifier.isPublic(cls.modifiers))
    }

    @Test
    fun calculationResult_isPublic() {
        val cls = Class.forName("com.vedicmath.app.models.CalculationResult")
        assertTrue("CalculationResult should be public", Modifier.isPublic(cls.modifiers))
    }
}