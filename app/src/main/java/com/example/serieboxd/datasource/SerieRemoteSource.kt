package com.example.serieboxd.datasource

import com.example.serieboxd.network.TmdbApiService
import com.example.serieboxd.network.TmdbTvShow

class SerieRemoteSource(private val api: TmdbApiService) {

    suspend fun getPopular(): List<TmdbTvShow> = api.getPopular().results

    suspend fun getTopRated(): List<TmdbTvShow> = api.getTopRated().results

    suspend fun getOnAir(): List<TmdbTvShow> = api.getOnAir().results

    suspend fun getDetails(id: Int): TmdbTvShow = api.getDetails(id)
}
