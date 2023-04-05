package com.example.room.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDAO {
    @Query("SELECT * FROM list_items ORDER BY item ASC")
    fun getItems(): Flow<List<ListItem>>

    // tutte suspend perchè sono "one shot"
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: ListItem)

    @Delete
    suspend fun delete(item: ListItem)

    @Query("DELETE FROM list_items")
    suspend fun deleteAll()
}