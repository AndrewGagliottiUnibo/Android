package com.example.traveldiary.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.traveldiary.data.Place
import com.example.traveldiary.data.PlacesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val repository: PlacesRepository
) : ViewModel() {

    val places = repository.places

    fun addNewPlace(place: Place) = viewModelScope.launch {
        repository.insertNewPlace(place)
    }

    private var _placeSelected: Place? = null
    val placeSelected
        get() = _placeSelected

    fun selectPlace(place: Place) {
        _placeSelected = place
    }
}