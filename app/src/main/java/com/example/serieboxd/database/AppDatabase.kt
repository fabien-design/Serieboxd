package com.example.serieboxd.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.serieboxd.dao.CachedSerieDao
import com.example.serieboxd.dao.SerieDao
import com.example.serieboxd.data.entities.CachedSerie
import com.example.serieboxd.data.entities.Serie

@Database(entities = [Serie::class, CachedSerie::class], exportSchema = false, version = 5)
abstract class AppDatabase : RoomDatabase() {
    abstract fun serieDao(): SerieDao
    abstract fun cachedSerieDao(): CachedSerieDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "items_table"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
