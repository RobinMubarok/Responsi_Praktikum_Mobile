package com.example.movieexplorer.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieexplorer.ui.screen.DetailScreen
import com.example.movieexplorer.ui.screen.HomeScreen
import com.example.movieexplorer.viewmodel.ShowViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // ViewModel dibuat di sini (scope Activity) agar dipakai bersama oleh Home dan Detail.
    val viewModel: ShowViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onShowClick = { showId -> navController.navigate("detail/$showId") }
            )
        }
        composable(
            route = "detail/{showId}",
            arguments = listOf(navArgument("showId") { type = NavType.IntType })
        ) { backStackEntry ->
            val showId = backStackEntry.arguments?.getInt("showId") ?: -1
            DetailScreen(
                showId = showId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
