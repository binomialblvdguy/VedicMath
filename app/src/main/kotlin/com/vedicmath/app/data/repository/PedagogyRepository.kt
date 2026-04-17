package com.vedicmath.app.data.repository

import com.vedicmath.app.domain.models.MethodObservation

/**
 * Repository for pedagogical content and observations.
 */
object PedagogyRepository {

    private val simpleMultiplierObservations: List<MethodObservation> = listOf(
        MethodObservation(
            id = "obs_1",
            shortcutName = "Pattern Recognition",
            proof = "Multipliers like 5 and 11 have natural shortcuts (10/2 or n+n), while 3 and 7 require more steps.",
            mentalPattern = "Adapt the approach to the number rather than using a one-size-fits-all method.",
            commonMistakes = listOf("Using complex ratios for simple numbers", "Forgetting the base shift")
        ),
        MethodObservation(
            id = "obs_2",
            shortcutName = "The Decimal Challenge",
            proof = "Methods optimized for even numbers (like divide by 2) become messy with odd numbers (235 / 2 = 117.5).",
            mentalPattern = "If the result isn't a whole number, revert to the Universal Pattern.",
            commonMistakes = listOf("Decimal alignment errors", "Mental fatigue from fraction handling")
        )
    )

    private val twoDigitObservations: List<MethodObservation> = listOf(
        MethodObservation(
            id = "obs_3",
            shortcutName = "Vertical and Crosswise",
            proof = "(10a + b)(10c + d) = 100(ac) + 10(ad + bc) + bd",
            mentalPattern = "Visualize the 'I X I' motion: Vertical, then Crosswise, then Vertical.",
            commonMistakes = listOf("Forgetting to carry the tens digit", "Adding cross-products incorrectly")
        )
    )

    private val verticalCrosswiseObservations: List<MethodObservation> = listOf(
        MethodObservation(
            id = "obs_4",
            shortcutName = "The Universal Pattern",
            proof = "Works for numbers of any size by repeating the crosswise motion.",
            mentalPattern = "One method that works everywhere, eliminating the need for special cases.",
            commonMistakes = listOf("Losing track of place value in larger numbers")
        )
    )

    private val allObservations: List<MethodObservation> =
        simpleMultiplierObservations + twoDigitObservations + verticalCrosswiseObservations

    fun getSimpleMultiplierObservations(): List<MethodObservation> = simpleMultiplierObservations

    fun getTwoDigitObservations(): List<MethodObservation> = twoDigitObservations

    fun getVerticalCrosswiseObservations(): List<MethodObservation> = verticalCrosswiseObservations

    fun getObservationById(id: String): MethodObservation? = allObservations.find { it.id == id }
}
