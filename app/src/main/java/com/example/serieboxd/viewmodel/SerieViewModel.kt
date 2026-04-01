package com.example.serieboxd.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.serieboxd.data.entities.Serie
import com.example.serieboxd.repository.SerieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SerieViewModel(private val repository: SerieRepository) : ViewModel() {
    val inProgressSeries: StateFlow<List<Serie>> = repository.allSeries
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _popularSeries = MutableStateFlow<List<Serie>>(emptyList())
    val popularSeries: StateFlow<List<Serie>> = _popularSeries.asStateFlow()

    // Meilleures séries depuis TMDB
    private val _topRatedSeries = MutableStateFlow<List<Serie>>(emptyList())
    val topRatedSeries: StateFlow<List<Serie>> = _topRatedSeries.asStateFlow()

    // Nouvelles séries en cours de diffusion depuis TMDB
    private val _onAirSeries = MutableStateFlow<List<Serie>>(emptyList())
    val onAirSeries: StateFlow<List<Serie>> = _onAirSeries.asStateFlow()

    private val _serieDetail = MutableStateFlow<Serie?>(null)
    val serieDetail: StateFlow<Serie?> = _serieDetail.asStateFlow()

    fun loadDetail(id: Int) {
        _serieDetail.value = null
        viewModelScope.launch {
            try {
                _serieDetail.value = repository.getSerieDetails(id)
            } catch (e: Exception) {
                Log.e("TMDB", "Detail fetch failed for id=$id", e)
            }
        }
    }

    init {
        fetchTmdbData()
    }

    private fun fetchTmdbData() {
        viewModelScope.launch {
            try {
                val results = repository.getPopularSeries()
                Log.d("TMDB", "Popular: ${results.size} series (cache or network)")
                _popularSeries.value = results
            } catch (e: Exception) {
                Log.e("TMDB", "Popular fetch failed", e)
            }
        }
        viewModelScope.launch {
            try {
                val results = repository.getTopRatedSeries()
                Log.d("TMDB", "Top Rated: ${results.size} series (cache or network)")
                _topRatedSeries.value = results
            } catch (e: Exception) {
                Log.e("TMDB", "Top Rated fetch failed", e)
            }
        }
        viewModelScope.launch {
            try {
                val results = repository.getOnAirSeries()
                Log.d("TMDB", "On Air: ${results.size} series (cache or network)")
                _onAirSeries.value = results
            } catch (e: Exception) {
                Log.e("TMDB", "On Air fetch failed", e)
            }
        }
    }
}
