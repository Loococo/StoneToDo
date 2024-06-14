package app.loococo.presentation.calendar

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val calendarRoute = "calendar_route"

fun NavGraphBuilder.calendarScreen() {
    composable(
        route = calendarRoute,
        enterTransition = { null },
        exitTransition = { null },
        popEnterTransition = { null },
        popExitTransition = { null }
    ) {
        CalendarRoute()
    }
}

fun NavController.navigateToCalendar(navOptions: NavOptions) {
    this.navigate(calendarRoute, navOptions)
}
