package com.vedicmath.app.data.repository

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class PedagogyRepositoryTest {

    @Test
    fun getObservationById_returnsExpectedObservation_forKnownId() {
        val observation = PedagogyRepository.getObservationById("obs_1")

        assertNotNull(observation)
        assertEquals("obs_1", observation!!.id)
        assertEquals("Pattern Recognition", observation.shortcutName)
    }

    @Test
    fun getObservationById_returnsNull_forUnknownId() {
        val observation = PedagogyRepository.getObservationById("does_not_exist")

        assertNull(observation)
    }
}
