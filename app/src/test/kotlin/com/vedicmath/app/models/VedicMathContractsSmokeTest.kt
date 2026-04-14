package com.vedicmath.app.models

import org.junit.Test
import org.junit.Assert.assertNotNull
import java.lang.ClassNotFoundException

class VedicMathContractsSmokeTest {

    @Test
    fun methodChoiceExists() {
        val cls = try {
            Class.forName("com.vedicmath.app.models.MethodChoice")
        } catch (e: ClassNotFoundException) {
            null
        }
        assertNotNull("MethodChoice class not found in package com.vedicmath.app.models", cls)
    }

    @Test
    fun calculationResultExists() {
        val cls = try {
            Class.forName("com.vedicmath.app.models.CalculationResult")
        } catch (e: ClassNotFoundException) {
            null
        }
        assertNotNull("CalculationResult class not found in package com.vedicmath.app.models", cls)
    }
}