package app.loococo.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.loococo.presentation.component.DoItTodoPopup
import app.loococo.presentation.component.rememberShowPopupState

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
    val todoListState by viewModel.todoList.collectAsStateWithLifecycle()
    val todoListMap by viewModel.todoListMap.collectAsStateWithLifecycle()
    val showPopupState = rememberShowPopupState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CalendarScreen(
            currentDay = currentDayState,
            calendarType = calendarTypeState,
            selectedDate = selectedDateState,
            todoListMap = todoListMap,
            onCalendarNavigation = viewModel::onCalendarNavigation,
            onCalendarTypeChange = viewModel::onCalendarTypeChange,
            onDateSelected = viewModel::updateSelectedDate,
            onDateRange = viewModel::dateRange,
        )
        Spacer(modifier = Modifier.height(5.dp))
        Box(modifier = Modifier.weight(1f)) {
            TodoListScreen(
                list = todoListState,
                onCheckedChange = viewModel::changeTodoStatus,
                onShowRequest = {
                    showPopupState.showPopup()
                }
            )
        }
        AddToDo(onAddTodo = viewModel::insert)
    }

    if (showPopupState.showPopupState) {
        DoItTodoPopup(
            onEditClick = {

            },
            onDeleteClick = {

            },
            onDismissRequest = showPopupState::dismissPopup
        )
    }
}