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

    @GET("discover/tv")
    suspend fun discoverTv(
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("vote_average.gte") voteAverageGte: Int = 5,
        @Query("vote_average.lte") voteAverageLte: Int = 10,
        @Query("with_genres") withGenres: String? = null,
        @Query("language") language: String = "en-US"
    ): TmdbResponse

    @GET("tv/{id}")
    suspend fun getDetails(
        @Path("id") id: Int,
        @Query("language") language: String = "en-US"
    ): TmdbTvShow
}
