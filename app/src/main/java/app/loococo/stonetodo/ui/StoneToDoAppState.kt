package app.loococo.stonetodo.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import app.loococo.presentation.calendar.calendarRoute
import app.loococo.presentation.calendar.navigateToCalendar
import app.loococo.presentation.home.homeRoute
import app.loococo.presentation.home.navigateToHome
import app.loococo.presentation.setting.navigateToSetting
import app.loococo.presentation.setting.settingRoute
import app.loococo.stonetodo.R
import app.loococo.stonetodo.navigation.TopLevelDestination
import app.loococo.stonetodo.navigation.TopLevelDestination.CALENDAR
import app.loococo.stonetodo.navigation.TopLevelDestination.HOME
import app.loococo.stonetodo.navigation.TopLevelDestination.SETTING

@Composable
fun rememberStoneToDoAppState(
    navController: NavHostController = rememberNavController(),
): StoneToDoAppState {
    return remember(
        navController,
    ) {
        StoneToDoAppState(
            navController = navController
        )
    }
}

@Stable
class StoneToDoAppState(
    val navController: NavHostController
) {

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            homeRoute -> HOME
            calendarRoute -> CALENDAR
            settingRoute -> SETTING
            else -> null
        }

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace(sectionName = "Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }

            when (topLevelDestination) {
                HOME -> {
                    navController.navigateToHome(topLevelNavOptions)
                }

                CALENDAR -> {
                    navController.navigateToCalendar(topLevelNavOptions)
                }

                SETTING -> {
                    navController.navigateToSetting(topLevelNavOptions)
                }
            }
        }
    }
}