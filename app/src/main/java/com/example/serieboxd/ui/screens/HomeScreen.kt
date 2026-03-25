package com.example.serieboxd.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.serieboxd.R
import com.example.serieboxd.ui.components.card.AppSerieCard
import com.example.serieboxd.ui.components.card.AppSerieCardVariant
import com.example.serieboxd.ui.components.card.SeriesItem
import com.example.serieboxd.ui.theme.SerieboxdTheme

@Composable
fun HomeScreen(navController: NavController) {
    val series = listOf(
        SeriesItem(1, "Breaking Bad", "Crime · Drame", 2008, R.drawable.breaking_bad, 2, 6, 8, 0.78f),
        SeriesItem(2, "The Last of Us", "Action · SF", 2023, R.drawable.breaking_bad, 1, 3, 12, 0.25f),
        SeriesItem(3, "House of Dragon", "Fantaisie", 2022, R.drawable.breaking_bad, currentSeason = 1, totalEpisodes = 10),
        SeriesItem(4, "Severance", "Thriller · SF", 2022, R.drawable.breaking_bad, currentSeason = 1, totalEpisodes = 24, rating = 0.55f),
        SeriesItem(5, "The Bear", "Drame", 2022, R.drawable.breaking_bad, currentSeason = 1, totalEpisodes = 6),
    )

    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        InProgressSerieboxdSection(navController, series)
        NewOnSerieboxdSection(navController, series)
        PopularSerieboxdSection(navController, series)
        TopRatedSerieboxdSection(navController, series)
    }
}

@Composable
fun InProgressSerieboxdSection(navController: NavController, series: List<SeriesItem>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(title = "In Progress")
        LazyRow (horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items(series) { item ->
                AppSerieCard(
                    item = item,
                    onClick = {
                    // navController.navigate("detail/${item.id}")
                    },
                    variant = AppSerieCardVariant.IN_PROGRESS
                )
            }
        }
    }
}

@Composable
fun NewOnSerieboxdSection(navController: NavController, series: List<SeriesItem>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(title = "New on Serieboxd")
        LazyRow (horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items(series) { item ->
                AppSerieCard(item = item, onClick = {
                    // navController.navigate("detail/${item.id}")
                })
            }
        }
    }
}

@Composable
fun PopularSerieboxdSection(navController: NavController, series: List<SeriesItem>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(title = "Popular")
        LazyRow (horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items(series) { item ->
                AppSerieCard(item = item, onClick = {
                    // navController.navigate("detail/${item.id}")
                })
            }
        }
    }
}

@Composable
fun TopRatedSerieboxdSection(navController: NavController, series: List<SeriesItem>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(title = "Top Rated")
        LazyRow (horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items(series) { item ->
                AppSerieCard(item = item, onClick = {
                    // navController.navigate("detail/${item.id}")
                })
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

@Preview(showBackground = true, backgroundColor = 0xFFFFFEE9)
@Composable
fun LandingScreenPreview() {
    SerieboxdTheme {
        HomeScreen(navController = rememberNavController())
    }
}
