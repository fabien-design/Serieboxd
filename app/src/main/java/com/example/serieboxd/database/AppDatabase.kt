package com.example.serieboxd.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.serieboxd.dao.SerieDao
import com.example.serieboxd.data.entities.Serie

@Database(entities = [Serie::class], exportSchema = true, version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun serieDao(): SerieDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context
        ): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "items_table"
                )
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}