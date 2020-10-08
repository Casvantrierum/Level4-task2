package com.example.level4_task2.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.level4_task2.dao.GameDao
import com.example.level4_task2.database.GameListRoomDatabase
import com.example.level4_task2.model.Game


class GameRepository(context: Context) {

    private val gameDao: GameDao

    init {
        val database = GameListRoomDatabase.getDatabase(context)
        gameDao = database!!.gameDao()
    }

    suspend fun getAllGames(): List<Game> {
        return gameDao.getAllGames()
    }

    suspend fun insertGame(game: Game) {
        gameDao.insertGame(game)
    }

    suspend fun deleteGame(game: Game) {
        gameDao.deleteGame(game)
    }

    suspend fun deleteAllGames() {
        gameDao.deleteAllGames()
    }

    suspend fun getLatestGame(): List<Game> {
        return gameDao.getLatestGame()
    }

    suspend fun getStats(): List<Int> {
        return listOf(
            gameDao.getStatsWin(), gameDao.getStatsDraw() , gameDao.getStatsLoss()
        )
    }
}
