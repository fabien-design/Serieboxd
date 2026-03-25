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
import com.example.serieboxd.repository.SerieRepository
import com.example.serieboxd.ui.components.AppBottomBar
import com.example.serieboxd.ui.components.AppTopBar
import com.example.serieboxd.ui.screens.HomeScreen
import com.example.serieboxd.ui.theme.SerieboxdTheme
import com.example.serieboxd.viewmodel.SerieViewModel
import com.example.serieboxd.viewmodel.SerieViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: SerieViewModel by viewModels {
        val db = AppDatabase.getDatabase(applicationContext)
        val localSource = SerieLocalSource(db.serieDao())
        val repository = SerieRepository(localSource)
        SerieViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SerieboxdTheme {
                AppContent(viewModel)
            }
        }
    }
}

@Composable
fun AppContent(viewModel: SerieViewModel) {
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
                composable("login") {
                    Text("login")
                }
                composable("register") {
                    Text("register")
                }
                composable("discover") {
                    Text("Discover")
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
