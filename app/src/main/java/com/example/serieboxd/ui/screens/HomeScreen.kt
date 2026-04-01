package com.example.serieboxd.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.serieboxd.data.entities.Serie
import com.example.serieboxd.ui.components.card.AppSerieCard
import com.example.serieboxd.ui.components.card.AppSerieCardVariant
import com.example.serieboxd.viewmodel.SerieViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: SerieViewModel) {
    val inProgress by viewModel.inProgressSeries.collectAsStateWithLifecycle()
    val onAir by viewModel.onAirSeries.collectAsStateWithLifecycle()
    val popular by viewModel.popularSeries.collectAsStateWithLifecycle()
    val topRated by viewModel.topRatedSeries.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        InProgressSerieboxdSection(navController, inProgress)
        NewOnSerieboxdSection(navController, onAir)
        PopularSerieboxdSection(navController, popular)
        TopRatedSerieboxdSection(navController, topRated)
    }
}

@Composable
fun InProgressSerieboxdSection(navController: NavController, series: List<Serie>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(title = "In Progress")
        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items(series) { item ->
                AppSerieCard(
                    item = item,
                    variant = AppSerieCardVariant.IN_PROGRESS,
                    onClick = { /* navController.navigate("detail/${item.id}") */ }
                )
            }
        }
    }
}

@Composable
fun NewOnSerieboxdSection(navController: NavController, series: List<Serie>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(title = "New on Serieboxd")
        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items(series) { item ->
                AppSerieCard(
                    item = item,
                    onClick = { navController.navigate("detail/${item.id}") }
                )
            }
        }
    }
}

@Composable
fun PopularSerieboxdSection(navController: NavController, series: List<Serie>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(title = "Popular")
        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items(series) { item ->
                AppSerieCard(
                    item = item,
                    onClick = { navController.navigate("detail/${item.id}") }
                )
            }
        }
    }
}

@Composable
fun TopRatedSerieboxdSection(navController: NavController, series: List<Serie>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(title = "Top Rated")
        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items(series) { item ->
                AppSerieCard(
                    item = item,
                    onClick = { navController.navigate("detail/${item.id}") }
                )
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
}
