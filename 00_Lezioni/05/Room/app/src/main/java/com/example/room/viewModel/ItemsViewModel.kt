package com.example.room.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.room.ItemRepository
import com.example.room.data.ListItem
import kotlinx.coroutines.launch

class ItemsViewModel(private val repository: ItemRepository): ViewModel() {

    val allItems = repository.allItems

    fun addItem(item: ListItem) = viewModelScope.launch {
        repository.insertItem(item)
    }

    fun deleteItem(item: ListItem) = viewModelScope.launch {
        repository.deleteItem(item)
    }

    fun clearItems() = viewModelScope.launch {
        repository.deleteAllItems()
    }

}

class ItemsViewModelFactory(private val repository: ItemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ItemsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}