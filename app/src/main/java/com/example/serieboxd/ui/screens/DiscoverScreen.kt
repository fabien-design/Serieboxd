package com.example.serieboxd.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.serieboxd.network.TMDB_GENRE_MAP
import com.example.serieboxd.ui.components.card.AppSerieCard
import com.example.serieboxd.ui.components.card.AppSerieCardVariant
import com.example.serieboxd.viewmodel.CatalogFilter
import com.example.serieboxd.viewmodel.DiscoverViewModel
import com.example.serieboxd.viewmodel.RatingsFilter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverScreen(navController: NavController, viewModel: DiscoverViewModel) {
    val lazyItems = viewModel.series.collectAsLazyPagingItems()
    val filters by viewModel.filters.collectAsStateWithLifecycle()

    var showRatingsSheet by remember { mutableStateOf(false) }
    var showCatalogSheet by remember { mutableStateOf(false) }
    var showGenreSheet by remember { mutableStateOf(false) }

    LaunchedEffect(lazyItems.loadState) {
        Log.d("Discover", "loadState — refresh=${lazyItems.loadState.refresh}, append=${lazyItems.loadState.append}, itemCount=${lazyItems.itemCount}")
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Search...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 16.dp)
        )

        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = filters.ratings != RatingsFilter.DEFAULT,
                onClick = { showRatingsSheet = true },
                label = { Text(filters.ratings.label) }
            )
            FilterChip(
                selected = filters.catalog != CatalogFilter.TRENDING,
                onClick = { showCatalogSheet = true },
                label = { Text(filters.catalog.label) }
            )
            FilterChip(
                selected = filters.genreId != null,
                onClick = { showGenreSheet = true },
                label = { Text(TMDB_GENRE_MAP[filters.genreId] ?: "Genre") }
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(lazyItems.itemCount) { index ->
                val serie = lazyItems[index]
                if (serie != null) {
                    AppSerieCard(
                        item = serie,
                        variant = AppSerieCardVariant.POSTER_ONLY,
                        width = null,
                        onClick = { navController.navigate("detail/${serie.id}") }
                    )
                }
            }

            when (val append = lazyItems.loadState.append) {
                is LoadState.Loading -> item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is LoadState.Error -> item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    ) {
                        Text(
                            text = append.error.localizedMessage ?: "Erreur de chargement",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
                else -> {}
            }
        }
    }

    if (showRatingsSheet) {
        ModalBottomSheet(
            onDismissRequest = { showRatingsSheet = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            LazyColumn {
                items(RatingsFilter.entries.size) { index ->
                    val rating = RatingsFilter.entries[index]
                    ListItem(
                        headlineContent = { Text(rating.label) },
                        leadingContent = {
                            RadioButton(
                                selected = filters.ratings == rating,
                                onClick = null
                            )
                        },
                        modifier = Modifier.clickable {
                            viewModel.setRatings(rating)
                            showRatingsSheet = false
                        }
                    )
                }
            }
        }
    }

    if (showCatalogSheet) {
        ModalBottomSheet(
            onDismissRequest = { showCatalogSheet = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            LazyColumn {
                items(CatalogFilter.entries.size) { index ->
                    val catalog = CatalogFilter.entries[index]
                    ListItem(
                        headlineContent = { Text(catalog.label) },
                        leadingContent = {
                            RadioButton(
                                selected = filters.catalog == catalog,
                                onClick = null
                            )
                        },
                        modifier = Modifier.clickable {
                            viewModel.setCatalog(catalog)
                            showCatalogSheet = false
                        }
                    )
                }
            }
        }
    }

    if (showGenreSheet) {
        ModalBottomSheet(
            onDismissRequest = { showGenreSheet = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            LazyColumn {
                item {
                    ListItem(
                        headlineContent = { Text("Tous les genres") },
                        leadingContent = {
                            RadioButton(
                                selected = filters.genreId == null,
                                onClick = null
                            )
                        },
                        modifier = Modifier.clickable {
                            viewModel.setGenre(null)
                            showGenreSheet = false
                        }
                    )
                }
                items(TMDB_GENRE_MAP.size) { index ->
                    val (genreId, genreName) = TMDB_GENRE_MAP.entries.elementAt(index)
                    ListItem(
                        headlineContent = { Text(genreName) },
                        leadingContent = {
                            RadioButton(
                                selected = filters.genreId == genreId,
                                onClick = null
                            )
                        },
                        modifier = Modifier.clickable {
                            viewModel.setGenre(genreId)
                            showGenreSheet = false
                        }
                    )
                }
            }
        }
    }
}
