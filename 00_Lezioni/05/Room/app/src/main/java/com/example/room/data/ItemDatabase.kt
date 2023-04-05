package com.example.room.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Passo la classe come argomento e la versione dl db, in questo caso 1 perchè non è stato creato
// creazione DB
@Database(entities = [ListItem::class], version = 1, exportSchema = true)
abstract class ItemDatabase : RoomDatabase() {

    abstract fun itemDAO(): ItemDAO

    companion object {
        @Volatile
        private var INSTANCE: ItemDatabase ?= null

        fun getDatabase(context: Context): ItemDatabase {
            // se l'istanza è nulla creo l'istanza del db in modo che sia accessibile
            // da un solo thread alla volta
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemDatabase::class.java,
                    "items_database"
                ).build()
                INSTANCE = instance

                instance
            }
        }
    }

}