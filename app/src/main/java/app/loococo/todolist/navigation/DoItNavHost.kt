package app.loococo.todolist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import app.loococo.presentation.home.homeRoute
import app.loococo.presentation.home.homeScreen

@Composable
fun DoItNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = homeRoute
    ) {
        homeScreen()
    }
}