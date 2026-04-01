package com.example.serieboxd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.serieboxd.database.AppDatabase
import com.example.serieboxd.datasource.SerieLocalSource
import com.example.serieboxd.datasource.SerieRemoteSource
import com.example.serieboxd.network.RetrofitClient
import com.example.serieboxd.repository.SerieRepository
import com.example.serieboxd.ui.components.AppBottomBar
import com.example.serieboxd.ui.components.AppTopBar
import com.example.serieboxd.ui.screens.DetailScreen
import com.example.serieboxd.ui.screens.DiscoverScreen
import com.example.serieboxd.ui.screens.HomeScreen
import com.example.serieboxd.ui.theme.SerieboxdTheme
import com.example.serieboxd.viewmodel.DiscoverViewModel
import com.example.serieboxd.viewmodel.DiscoverViewModelFactory
import com.example.serieboxd.viewmodel.SerieViewModel
import com.example.serieboxd.viewmodel.SerieViewModelFactory

class MainActivity : ComponentActivity() {

    private val repository by lazy {
        val db = AppDatabase.getDatabase(applicationContext)
        val localSource = SerieLocalSource(db.serieDao())
        val remoteSource = SerieRemoteSource(RetrofitClient.tmdbApi)
        SerieRepository(localSource, remoteSource, db.cachedSerieDao())
    }

    private val viewModel: SerieViewModel by viewModels {
        SerieViewModelFactory(repository)
    }

    private val discoverViewModel: DiscoverViewModel by viewModels {
        DiscoverViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SerieboxdTheme {
                AppContent(viewModel, discoverViewModel)
            }
        }
    }
}

@Composable
fun AppContent(viewModel: SerieViewModel, discoverViewModel: DiscoverViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0),
        topBar = {
            AppTopBar(navController)
        },
        bottomBar = {
            if (currentRoute != "landing" && currentRoute != "login" && currentRoute != "register") {
                AppBottomBar(
                    navController = navController,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = "home",
            ) {
                composable("home") {
                    HomeScreen(navController = navController, viewModel = viewModel)
                }
                composable("detail/{serieId}") { backStackEntry ->
                    val serieId = backStackEntry.arguments?.getString("serieId")?.toIntOrNull() ?: return@composable
                    DetailScreen(serieId = serieId, viewModel = viewModel)
                }
                composable("login") {
                    Text("login")
                }
                composable("register") {
                    Text("register")
                }
                composable("discover") {
                    DiscoverScreen(navController = navController, viewModel = discoverViewModel)
                }
                composable("watchlist") {
                    Text("Watchlist")
                }
                composable("profile") {
                    Text("Profile")
                }
            }
        }
    }
}
