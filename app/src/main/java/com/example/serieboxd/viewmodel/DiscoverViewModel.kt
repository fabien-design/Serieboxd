package com.example.serieboxd.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.serieboxd.data.entities.Serie
import com.example.serieboxd.datasource.DiscoverPagingSource
import com.example.serieboxd.repository.SerieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update

class DiscoverViewModel(private val repository: SerieRepository) : ViewModel() {

    private val _filters = MutableStateFlow(DiscoverFilters())
    val filters: StateFlow<DiscoverFilters> = _filters.asStateFlow()

    val series: Flow<PagingData<Serie>> = _filters.flatMapLatest { filters ->
        Pager(PagingConfig(pageSize = 20, enablePlaceholders = false)) {
            DiscoverPagingSource(repository, filters)
        }.flow
    }.cachedIn(viewModelScope)

    fun setSearchRequest(request: String) {
        _filters.update { it.copy(searchRequest = request) }
    }

    fun setRatings(rating: RatingsFilter) {
        _filters.update { it.copy(ratings = rating) }
    }

    fun setCatalog(catalog: CatalogFilter) {
        _filters.update { it.copy(catalog = catalog) }
    }

    fun setGenre(genreId: Int?) {
        _filters.update { it.copy(genreId = genreId) }
    }
}
