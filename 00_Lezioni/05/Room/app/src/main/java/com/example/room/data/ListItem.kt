package com.example.room.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_items")
data class ListItem (
    @PrimaryKey
    // cambio il nome della colonna nel db
    @ColumnInfo(name = "item")
    var listItem: String
)