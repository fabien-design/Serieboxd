package com.example.serieboxd.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.serieboxd.data.entities.Serie
import kotlinx.coroutines.flow.Flow

@Dao
interface SerieDao {
    @Query("SELECT * FROM Serie")
    fun getAll(): Flow<List<Serie>>

    @Query("SELECT * FROM Serie WHERE id = :id")
    fun getById(id: Int): Flow<Serie>

    @Query("SELECT * FROM Serie WHERE title LIKE '%' || :title || '%'")
    fun getByTitle(title: String): Flow<Serie>
}
