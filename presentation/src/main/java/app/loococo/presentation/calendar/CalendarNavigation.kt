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
import app.loococo.presentation.component.animatedComposable

const val calendarRoute = "calendar_route"

fun NavGraphBuilder.calendarScreen() {
    animatedComposable(route = calendarRoute) {
        CalendarRoute()
    }
}

fun NavController.navigateToCalendar(navOptions: NavOptions) {
    this.navigate(calendarRoute, navOptions)
}
