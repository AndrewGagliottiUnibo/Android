package com.example.traveldiary.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "places")
data class Place (
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "place_name")
    var placeName: String,

    @ColumnInfo(name = "place_description")
    var placeDescription: String,

    @ColumnInfo(name = "travel_date")
    var travelDate: String
)