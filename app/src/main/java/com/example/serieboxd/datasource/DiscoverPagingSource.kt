package com.example.serieboxd.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.serieboxd.data.entities.Serie
import com.example.serieboxd.repository.SerieRepository
import com.example.serieboxd.viewmodel.DiscoverFilters

class DiscoverPagingSource(
    private val repository: SerieRepository,
    private val filters: DiscoverFilters
) : PagingSource<Int, Serie>() {

    override fun getRefreshKey(state: PagingState<Int, Serie>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Serie> {
        val page = params.key ?: 1
        Log.d("Discover", "load() — page=$page, catalog=${filters.catalog}, rating=${filters.ratings}, genre=${filters.genreId}")
        return try {
            val response = if (filters.searchRequest.isNotBlank()) {
                repository.searchTv(filters.searchRequest, page)
            } else {
                repository.discoverPaged(
                    page = page,
                    sortBy = filters.catalog.sortBy,
                    voteAverageGte = filters.ratings.voteAverageGte,
                    voteAverageLte = filters.ratings.voteAverageLte,
                    genreId = filters.genreId
                )
            }
            Log.d("Discover", "Réponse — page=${response.page}, total_pages=${response.total_pages}, résultats=${response.results.size}")
            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page >= response.total_pages) null else page + 1
            )
        } catch (e: Exception) {
            Log.e("Discover", "Erreur page=$page", e)
            LoadResult.Error(e)
        }
    }
}
