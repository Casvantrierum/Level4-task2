package com.example.level4_task2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "gameTable")
data class Game (
    @ColumnInfo(name = "player")
    var player: String,

    @ColumnInfo(name = "computer")
    var computer: String,

    @ColumnInfo(name = "result")
    var result: String,

    @ColumnInfo(name = "date")
    var date: Date,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
)