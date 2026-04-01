package com.example.serieboxd.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.serieboxd.BuildConfig
import com.example.serieboxd.data.entities.Serie

@Composable
fun AppSerieCard(
    item: Serie,
    variant: AppSerieCardVariant = AppSerieCardVariant.DEFAULT,
    width: Float? = null,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.width(width?.dp ?: 130.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            Box {
                AsyncImage(
                    model = item.posterPath?.let { "${BuildConfig.TMDB_IMAGE_BASE_URL}$it" },
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
                if (item.rating != null) {
                    CircularProgressWithText(
                        progress = item.rating,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(6.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF2C2C2A).copy(alpha = 0.75f))
                    )
                }
            }

            if (variant != AppSerieCardVariant.POSTER_ONLY) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (variant == AppSerieCardVariant.DEFAULT) {
                        Text(
                            text = item.genres,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = item.year.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    if (variant == AppSerieCardVariant.IN_PROGRESS) {
                        val progress = (item.currentEpisode?.toFloat() ?: 0f) / (item.totalEpisodes?.toFloat() ?: 1f)

                        LinearProgressIndicator(
                            progress = { progress },
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            gapSize = 0.dp,
                            drawStopIndicator = {},
                            modifier = Modifier.fillMaxWidth(),
                        )
                        Text(
                            text = "S${item.currentSeason} · EP${item.currentEpisode ?: 0}/${item.totalEpisodes}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CircularProgressWithText(
    progress: Float,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(38.dp)
    ) {
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxSize(),
            color = when {
                progress >= 0.7f -> Color(0xFF1D9E75)
                progress >= 0.4f -> Color(0xFFEF9F27)
                else -> Color(0xFFE24B4A)
            },
            strokeWidth = 4.dp,
            gapSize = 0.dp,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            Text(
                text = "${(progress * 100).toInt()}",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = "%",
                style = MaterialTheme.typography.labelSmall,
                fontSize = 6.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview
@Composable
fun AppSerieCardPreview() {
    val item = Serie(
        id = 1,
        title = "Breaking Bad",
        description = "Une série sur la drogue",
        genres = "Crime · Drame",
        year = 2008,
        posterPath = null,
        currentSeason = 2,
        currentEpisode = 6,
        totalEpisodes = 8,
        rating = 0.78f
    )
    AppSerieCard(item = item, onClick = {})
}
