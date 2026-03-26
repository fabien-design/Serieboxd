package com.example.serieboxd.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Serie (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val genres: String,
    val year: Int,
    val posterPath: String?,
    val backdropPath: String? = null,
    val currentSeason: Int? = null,
    val currentEpisode: Int? = null,
    val totalEpisodes: Int? = null,
    val rating: Float? = null,
    val creators: String? = null,
)