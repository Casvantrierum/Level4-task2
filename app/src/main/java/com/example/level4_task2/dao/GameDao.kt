package com.example.level4_task2.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.level4_task2.model.Game

@Dao
interface GameDao {

    @Query("SELECT * FROM gameTable")
    suspend fun getAllGames(): List<Game>

    @Insert
    suspend fun insertGame(game: Game)

    @Delete
    suspend fun deleteGame(game: Game)

    @Query("DELETE FROM gameTable")
    suspend fun deleteAllGames()

    @Query("SELECT * FROM gameTable ORDER BY `id` DESC LIMIT 1")
    suspend fun getLatestGame(): List<Game>

    @Query("SELECT COUNT(*) FROM gameTable WHERE result = 'win'")
    suspend fun getStatsWin(): Int

    @Query("SELECT COUNT(*) FROM gameTable WHERE result = 'draw'")
    suspend fun getStatsDraw(): Int

    @Query("SELECT COUNT(*) FROM gameTable WHERE result = 'loss'")
    suspend fun getStatsLoss(): Int
}