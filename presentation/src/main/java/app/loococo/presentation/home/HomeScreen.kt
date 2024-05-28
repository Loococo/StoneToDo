package app.loococo.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
internal fun HomeRoute() {
    HomeScreen()
}

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = hiltViewModel()
    val currentDayState by viewModel.currentDay.collectAsStateWithLifecycle()
    val calendarTypeState by viewModel.calendarType.collectAsStateWithLifecycle()
    val selectedDateState by viewModel.selectedDate.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        CalendarScreen(
            currentDay = currentDayState,
            calendarType = calendarTypeState,
            selectedDate = selectedDateState,
            onCalendarNavigation = viewModel::onCalendarNavigation,
            onCalendarTypeChange = viewModel::onCalendarTypeChange,
            onDateSelected = viewModel::updateSelectedDate
        )
    }
}
