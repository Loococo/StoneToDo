package app.loococo.presentation.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable


const val homeRoute = "home_route"

fun NavGraphBuilder.homeScreen() {
    composable(
        route = homeRoute,
        enterTransition = { null },
        exitTransition = { null },
        popEnterTransition = { null },
        popExitTransition = { null }
    ) {
        HomeRoute()
    }
}

fun NavController.navigateToHome(navOptions: NavOptions) {
    this.navigate(homeRoute, navOptions)
}

