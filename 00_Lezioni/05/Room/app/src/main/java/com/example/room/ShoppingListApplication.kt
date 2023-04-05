package com.example.room

import android.app.Application
import com.example.room.data.ItemDatabase

class ShoppingListApplication : Application(){
    // lazy --> the database and the repository are only created when they're needed
    private val database by lazy { ItemDatabase.getDatabase(this) }
    val repository by lazy { ItemRepository(database.itemDAO()) }
}