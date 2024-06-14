package app.loococo.stonetodo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import app.loococo.presentation.calendar.calendarRoute
import app.loococo.presentation.calendar.calendarScreen
import app.loococo.presentation.home.homeScreen
import app.loococo.presentation.setting.settingScreen
import app.loococo.stonetodo.ui.StoneToDoAppState

@Composable
fun StoneToDoNavHost(
    appState: StoneToDoAppState
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = calendarRoute
    ) {
        homeScreen()
        calendarScreen()
        settingScreen()
    }
}