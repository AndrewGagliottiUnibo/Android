package com.example.room

import androidx.annotation.WorkerThread
import com.example.room.data.ItemDAO
import com.example.room.data.ListItem
import kotlinx.coroutines.flow.Flow

class ItemRepository(private val itemDAO:ItemDAO) {

    val allItems: Flow<List<ListItem>> = itemDAO.getItems()

    //@WorkerThread Denotes that the annotated method should only be called on a worker thread.
    //By default Room runs suspend queries off the main thread
    @WorkerThread
    suspend fun insertItem(item: ListItem) {
        itemDAO.insert(item)
    }

    @WorkerThread
    suspend fun deleteItem(item: ListItem) {
        itemDAO.delete(item)
    }

    @WorkerThread
    suspend fun deleteAllItems() {
        itemDAO.deleteAll()
    }
}