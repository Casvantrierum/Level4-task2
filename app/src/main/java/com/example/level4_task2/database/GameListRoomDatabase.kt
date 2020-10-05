package com.example.level4_task2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.level4_task2.dao.GameDao
import com.example.level4_task2.model.Game

@Database(entities = [Game::class], version = 1, exportSchema = false)
abstract class GameListRoomDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {
        private const val DATABASE_NAME = "GAME_LIST_DATABASE"

        @Volatile
        private var gameListRoomDatabaseInstance: GameListRoomDatabase? = null

        fun getDatabase(context: Context): GameListRoomDatabase? {
            if (gameListRoomDatabaseInstance == null) {
                synchronized(GameListRoomDatabase::class.java) {
                    if (gameListRoomDatabaseInstance == null) {
                        gameListRoomDatabaseInstance =
                            Room.databaseBuilder(context.applicationContext,GameListRoomDatabase::class.java, DATABASE_NAME).build()
                    }
                }
            }
            return gameListRoomDatabaseInstance
        }
    }

}
