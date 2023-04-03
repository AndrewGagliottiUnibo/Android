package com.example.diceroll

class Dice(private val numSides: Int) {

    /**
     * Returns a random number based on dice sides.
     */
    fun roll(): Int {
        return (1..numSides).random()
    }
}