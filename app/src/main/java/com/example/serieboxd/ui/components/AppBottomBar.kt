package com.example.serieboxd.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.serieboxd.R
import com.example.serieboxd.ui.theme.SerieboxdTheme

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun AppBottomBar(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val items = listOf(
        R.drawable.outline_home_24 to "home",
        R.drawable.outline_explore to "discover",
        R.drawable.rss_feed to "watchlist",
        R.drawable.outline_user to "profile"
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val selectedIndex = items.indexOfFirst { it.second == currentRoute }.coerceAtLeast(0)

    val animatedIndex by animateFloatAsState(
        targetValue = selectedIndex.toFloat(),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "bottomBarIndicator"
    )

    val indicatorSize = 48.dp

    BoxWithConstraints(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .navigationBarsPadding()
            .height(64.dp)
    ) {
        val itemWidth = maxWidth / items.size

        // Sliding pill
        Box(
            modifier = Modifier
                .offset(x = itemWidth * animatedIndex + (itemWidth - indicatorSize) / 2)
                .align(Alignment.CenterStart)
                .size(indicatorSize)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        )

        Row(modifier = Modifier.fillMaxSize()) {
            items.forEach { (icon, value) ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        ) { navController.navigate(value) }

                ) {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = value,
                        tint = if (currentRoute == value) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun AppBottomBarPreview() {
    SerieboxdTheme {
        AppBottomBar(
            navController = rememberNavController()
        )
    }
}
