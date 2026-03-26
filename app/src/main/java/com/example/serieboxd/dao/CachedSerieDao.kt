package com.example.serieboxd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.serieboxd.data.entities.CachedSerie

@Dao
interface CachedSerieDao {

    @Query("SELECT * FROM cached_serie WHERE category = :category")
    suspend fun getByCategory(category: String): List<CachedSerie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(series: List<CachedSerie>)

    @Query("DELETE FROM cached_serie WHERE category = :category")
    suspend fun deleteByCategory(category: String)

    @Query("SELECT MIN(cachedAt) FROM cached_serie WHERE category = :category")
    suspend fun getOldestCacheTime(category: String): Long?
}
