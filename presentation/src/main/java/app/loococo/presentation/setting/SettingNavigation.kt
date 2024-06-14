package app.loococo.presentation.setting

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable


const val settingRoute = "setting_route"

fun NavGraphBuilder.settingScreen() {
    composable(
        route = settingRoute,
        enterTransition = { null },
        exitTransition = { null },
        popEnterTransition = { null },
        popExitTransition = { null }
    ) {
        SettingRoute()
    }
}

fun NavController.navigateToSetting(navOptions: NavOptions) {
    this.navigate(settingRoute, navOptions)
}