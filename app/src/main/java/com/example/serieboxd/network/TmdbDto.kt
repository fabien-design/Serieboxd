package com.example.serieboxd.network

import com.example.serieboxd.data.entities.CachedSerie
import com.example.serieboxd.data.entities.Serie
import com.google.gson.annotations.SerializedName

data class TmdbResponse(
    val results: List<TmdbTvShow>
)

data class TmdbCreator(
    val id: Int,
    @SerializedName("name") val name: String
)

data class TmdbSeasonDetail(
    val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("air_date") val airDate: String?,
    @SerializedName("episode_count") val episodeCount: Int,
    @SerializedName("season_number") val seasonNumber: Int,
    @SerializedName("vote_average") val voteAverage: Double
)

data class TmdbGenre(
    val id: Int,
    @SerializedName("name") val name: String
)

data class TmdbTvShow(
    val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("first_air_date") val firstAirDate: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>?,
    @SerializedName("genres") val genres: List<TmdbGenre>?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("created_by") val creators: List<TmdbCreator>?,
    @SerializedName("in_production") val inProduction: Boolean?,
    @SerializedName("number_of_episodes") val numberOfEpisodes: Int?,
    @SerializedName("number_of_seasons") val numberOfSeasons: Int?,
    @SerializedName("status") val status: String?,
    @SerializedName("seasons") val seasons: List<TmdbSeasonDetail>?
)


val TMDB_GENRE_MAP = mapOf(
    10759 to "Action & Adventure",
    16 to "Animation",
    35 to "Comedy",
    80 to "Crime",
    99 to "Documentary",
    18 to "Drama",
    10751 to "Family",
    10762 to "Kids",
    9648 to "Mystery",
    10765 to "Sci-Fi & Fantasy",
    10766 to "Soap",
    10768 to "War & Politics",
    37 to "Western"
)

private fun TmdbTvShow.resolveGenres(): String =
    genres?.map { it.name }?.take(2)?.joinToString(" · ")?.ifEmpty { "—" }
        ?: genreIds?.mapNotNull { TMDB_GENRE_MAP[it] }?.take(2)?.joinToString(" · ")?.ifEmpty { "—" }
        ?: "—"

fun TmdbTvShow.toSerie() = Serie(
    id = id,
    title = name,
    description = overview,
    genres = resolveGenres(),
    year = firstAirDate?.take(4)?.toIntOrNull() ?: 0,
    posterPath = posterPath,
    backdropPath = backdropPath,
    rating = (voteAverage / 10).toFloat().coerceIn(0f, 1f),
    currentSeason = seasons?.maxByOrNull { it.seasonNumber }?.seasonNumber,
    currentEpisode = seasons?.maxByOrNull { it.episodeCount }?.episodeCount,
    totalEpisodes = seasons?.sumOf { it.episodeCount },
    creators = creators?.mapNotNull { it.name }?.take(2)?.joinToString(" · ")?.ifEmpty { null }
)


fun TmdbTvShow.toCachedSerie(category: String) = CachedSerie(
    id = id,
    title = name,
    description = overview,
    genres = resolveGenres(),
    year = firstAirDate?.take(4)?.toIntOrNull() ?: 0,
    posterPath = posterPath,
    backdropPath = backdropPath,
    rating = (voteAverage / 10).toFloat().coerceIn(0f, 1f),
    creators = creators?.mapNotNull { it.name }?.take(2)?.joinToString(" · ")?.ifEmpty { null },
    category = category,
    cachedAt = System.currentTimeMillis()
)
