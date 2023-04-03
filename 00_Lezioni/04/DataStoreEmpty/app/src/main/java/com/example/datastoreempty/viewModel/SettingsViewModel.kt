package com.example.datastore.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datastoreempty.data.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// Hilt deve sapere che questo è un viewmodel
@HiltViewModel
class SettingsViewModel @Inject constructor
    (private val settingsRepository: SettingsRepository) : ViewModel() {
        val theme = settingsRepository.preferenceFlow

    fun saveTheme(theme: String) {
        // è necessaria una coroutine: va nel dispatcher di I/O
        viewModelScope.launch {
            settingsRepository.saveToDataStore(theme)
        }
    }
}