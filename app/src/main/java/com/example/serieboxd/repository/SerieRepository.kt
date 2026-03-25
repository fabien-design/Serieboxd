package com.example.serieboxd.repository

import com.example.serieboxd.data.entities.Serie
import com.example.serieboxd.datasource.SerieLocalSource
import kotlinx.coroutines.flow.Flow

class SerieRepository(private val serieSource: SerieLocalSource) {
    //Get the data from the DB
    val allSeries: Flow<List<Serie>> = serieSource.getAllSeries()

    // Get the data from the DB by id
    fun getSerieById(id: Int): Flow<Serie> {
        return serieSource.getSerieById(id)
    }

    // Get the data from the DB by title
    fun getSeriesByTitle(title: String): Flow<Serie> {
        return serieSource.getSeriesByTitle(title)
    }
}