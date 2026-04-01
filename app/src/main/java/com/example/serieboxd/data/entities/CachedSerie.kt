package com.example.serieboxd.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_serie")
data class CachedSerie(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val genres: String,
    val year: Int,
    val posterPath: String?,
    val backdropPath: String?,
    val rating: Float?,
    val creators: String?,
    val category: String,
    val cachedAt: Long
)

fun CachedSerie.toSerie() = Serie(
    id = id,
    title = title,
    description = description,
    genres = genres,
    year = year,
    posterPath = posterPath,
    backdropPath = backdropPath,
    rating = rating,
    creators = creators
)
