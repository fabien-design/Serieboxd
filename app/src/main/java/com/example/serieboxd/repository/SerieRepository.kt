package com.example.serieboxd.repository

import com.example.serieboxd.dao.CachedSerieDao
import com.example.serieboxd.data.entities.Serie
import com.example.serieboxd.data.entities.toSerie
import com.example.serieboxd.datasource.SerieLocalSource
import com.example.serieboxd.datasource.SerieRemoteSource
import com.example.serieboxd.network.TmdbResponse
import com.example.serieboxd.network.TmdbTvShow
import com.example.serieboxd.network.toCachedSerie
import com.example.serieboxd.network.toSerie
import kotlinx.coroutines.flow.Flow


data class SerieList(
    val page: Int,
    val total_pages: Int,
    val results: List<Serie>
)



class SerieRepository(
    private val localSource: SerieLocalSource,
    private val remoteSource: SerieRemoteSource,
    private val cacheDao: CachedSerieDao
) {
    companion object {
        private const val CACHE_DURATION_MS = 24 * 60 * 60 * 1000L // 24 heures
    }

    val allSeries: Flow<List<Serie>> = localSource.getAllSeries()

    fun getSerieById(id: Int): Flow<Serie> = localSource.getSerieById(id)

    suspend fun getPopularSeries(): List<Serie> =
        getCategoryWithCache("popular") { remoteSource.getPopular() }

    suspend fun getTopRatedSeries(): List<Serie> =
        getCategoryWithCache("top_rated") { remoteSource.getTopRated() }

    suspend fun getOnAirSeries(): List<Serie> =
        getCategoryWithCache("on_air") { remoteSource.getOnAir() }

    suspend fun getSerieDetails(id: Int): Serie = remoteSource.getDetails(id).toSerie()

    suspend fun discoverPaged(page: Int,
                              sortBy: String,
                              voteAverageGte: Int,
                              voteAverageLte: Int,
                              genreId: Int?): SerieList {
        val response = remoteSource.discoverTv(
            page,
            sortBy,
            voteAverageGte,
            voteAverageLte,
            genreId
        )
        return SerieList(
            page = response.page,
            total_pages = response.total_pages,
            results = response.results.map { it.toSerie() }
        )
    }

    suspend fun searchTv(query: String, page: Int): SerieList {
        val response = remoteSource.searchTv(query, page)
        return SerieList(
            page = response.page,
            total_pages = response.total_pages,
            results = response.results.map { it.toSerie() }
        )
    }

    private suspend fun getCategoryWithCache(
        category: String,
        fetchFromApi: suspend () -> List<TmdbTvShow>
    ): List<Serie> {
        val oldestCacheTime = cacheDao.getOldestCacheTime(category)
        val isCacheValid = oldestCacheTime != null &&
            System.currentTimeMillis() - oldestCacheTime < CACHE_DURATION_MS

        if (isCacheValid) {
            return cacheDao.getByCategory(category).map { it.toSerie() }
        }

        val results = fetchFromApi()
        cacheDao.deleteByCategory(category)
        cacheDao.insertAll(results.map { it.toCachedSerie(category) })
        return results.map { it.toSerie() }
    }

}
