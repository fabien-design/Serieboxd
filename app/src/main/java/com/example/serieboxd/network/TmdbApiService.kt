package com.example.serieboxd.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {

    @GET("tv/popular")
    suspend fun getPopular(
        @Query("language") language: String = "en-US"
    ): TmdbResponse

    @GET("tv/top_rated")
    suspend fun getTopRated(
        @Query("language") language: String = "en-US"
    ): TmdbResponse

    @GET("tv/on_the_air")
    suspend fun getOnAir(
        @Query("language") language: String = "en-US"
    ): TmdbResponse

    @GET("tv/{id}")
    suspend fun getDetails(
        @Path("id") id: Int,
        @Query("language") language: String = "en-US"
    ): TmdbTvShow
}
