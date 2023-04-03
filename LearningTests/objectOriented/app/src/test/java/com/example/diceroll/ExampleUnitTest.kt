package com.example.diceroll

import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun generates_numbers() {

        val dice = Dice(6)
        val result = dice.roll()

        assertTrue("Value not correct", result in 1..6)
    }
}