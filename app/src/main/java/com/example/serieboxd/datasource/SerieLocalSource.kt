package com.example.serieboxd.datasource

import com.example.serieboxd.dao.SerieDao
import com.example.serieboxd.data.entities.Serie
import kotlinx.coroutines.flow.Flow

class SerieLocalSource(private val serieDao: SerieDao) {

    fun getAllSeries(): Flow<List<Serie>> {
        return serieDao.getAll()
    }

    fun getSerieById(id: Int): Flow<Serie> {
        return serieDao.getById(id)
    }

    fun getSeriesByTitle(title: String): Flow<Serie> {
        return serieDao.getByTitle(title)
    }
}