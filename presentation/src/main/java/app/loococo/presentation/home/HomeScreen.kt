package app.loococo.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.loococo.presentation.utils.DoItIcons

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
    ) {
        CalendarScreen(
            currentDay = currentDayState,
            calendarType = calendarTypeState,
            selectedDate = selectedDateState,
            onCalendarNavigation = viewModel::onCalendarNavigation,
            onCalendarTypeChange = viewModel::onCalendarTypeChange,
            onDateSelected = viewModel::updateSelectedDate
        )
        Spacer(modifier = Modifier.height(5.dp))
        Box(modifier = Modifier.weight(1f)) {

        }
        AddToDo()
    }
}

@Composable
fun AddToDo() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray, shape = RoundedCornerShape(10.dp))
        ) {
            BasicTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.weight(1f),
            )
            Icon(DoItIcons.Plus, contentDescription = "Next period")
        }
    }
}