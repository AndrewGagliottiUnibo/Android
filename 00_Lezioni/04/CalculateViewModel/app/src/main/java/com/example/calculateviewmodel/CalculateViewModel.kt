package com.example.calculateviewmodel

import androidx.lifecycle.ViewModel

class CalculateViewModel: ViewModel() {
    private var _value = 0
    val value
    get() = _value

    fun sum(number: Int) {
        _value = number + 1
    }
}