package app.loococo.presentation.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import app.loococo.presentation.component.animatedComposable


const val homeRoute = "home_route"

fun NavGraphBuilder.homeScreen() {
    animatedComposable(route = homeRoute) {
        HomeRoute()
    }
}

fun NavController.navigateToHome(navOptions: NavOptions) {
    this.navigate(homeRoute, navOptions)
}

