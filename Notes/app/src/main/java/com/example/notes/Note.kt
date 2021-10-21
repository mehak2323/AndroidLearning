package com.example.notes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Entity is annotation to tell us below class will act as entity
@Entity(tableName = "notes_table")
class Note(@ColumnInfo(name = "text") val text: String) {

    //Some columns can be inside class, instead of constructor variables
    @PrimaryKey(autoGenerate = true) val id = 0

}