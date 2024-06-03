package app.loococo.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.loococo.domain.model.CalendarNavigation
import app.loococo.domain.model.CalendarType
import app.loococo.domain.model.Todo
import app.loococo.domain.usecase.PreferencesUseCase
import app.loococo.domain.usecase.TodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val preferencesUseCase: PreferencesUseCase,
    private val todoUseCase: TodoUseCase
) : ViewModel() {

    private val _currentDayFlow: MutableStateFlow<LocalDate> = MutableStateFlow(LocalDate.now())
    val currentDay: StateFlow<LocalDate> = _currentDayFlow

    private val _calendarTypeFlow: MutableStateFlow<CalendarType> =
        MutableStateFlow(preferencesUseCase.getCalendarType())
    val calendarType: StateFlow<CalendarType> = _calendarTypeFlow

    private val _selectedDateFlow: MutableStateFlow<LocalDate> = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDateFlow

    private val _dateRangeFlow: MutableStateFlow<Pair<LocalDate, LocalDate>> =
        MutableStateFlow(Pair(LocalDate.now(), LocalDate.now()))
    val dateRange: StateFlow<Pair<LocalDate, LocalDate>> = _dateRangeFlow

    val todoList: StateFlow<List<Todo>> = _selectedDateFlow
        .flatMapLatest { selectedDate ->
            todoUseCase.getTodoList(selectedDate)
                .onStart { emit(emptyList()) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val todoListMap: StateFlow<Map<LocalDate, List<Todo>>> = _dateRangeFlow
        .flatMapLatest { dateRange ->
            todoUseCase.getTodoList(dateRange.first, dateRange.second)
                .onStart { emit(emptyList()) }
        }
        .map { todos ->
            todos.groupBy { it.date }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyMap()
        )

    fun onCalendarNavigation(navigation: CalendarNavigation) {
        val currentDayValue = _currentDayFlow.value
        val updatedDay = when (_calendarTypeFlow.value) {
            CalendarType.DayOfMonth -> {
                val newDay = when (navigation) {
                    CalendarNavigation.NavigateToNextPeriod -> currentDayValue.plusMonths(1)
                    CalendarNavigation.NavigateToPreviousPeriod -> currentDayValue.minusMonths(1)
                    else -> return
                }
                updateSelectedDate(newDay.withDayOfMonth(1))
                newDay
            }

            CalendarType.DayOfWeek -> {
                val newDay = when (navigation) {
                    CalendarNavigation.NavigateToNextPeriod -> currentDayValue.plusWeeks(1)
                    CalendarNavigation.NavigateToPreviousPeriod -> currentDayValue.minusWeeks(1)
                    else -> return
                }
                updateSelectedDate(newDay.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)))
                newDay
            }
        }
        _currentDayFlow.value = updatedDay
    }

    fun onCalendarTypeChange() {
        _calendarTypeFlow.value = when (_calendarTypeFlow.value) {
            CalendarType.DayOfMonth -> {
                updateSelectedDate(
                    _currentDayFlow.value.with(
                        TemporalAdjusters.previousOrSame(
                            DayOfWeek.SUNDAY
                        )
                    )
                )
                CalendarType.DayOfWeek
            }

            CalendarType.DayOfWeek -> {
                updateSelectedDate(_currentDayFlow.value.withDayOfMonth(1))
                CalendarType.DayOfMonth
            }
        }
        preferencesUseCase.saveCalendarType(_calendarTypeFlow.value)
    }

    fun updateSelectedDate(date: LocalDate) {
        _selectedDateFlow.value = date
    }

    fun insert(description: String) {
        val todo = Todo(date = _selectedDateFlow.value, description = description)
        viewModelScope.launch {
            todoUseCase.insert(todo)
        }
    }

    fun dateRange(startDate: LocalDate, endDate: LocalDate) {
        _dateRangeFlow.value = Pair(startDate, endDate)
    }

    fun changeTodoStatus(id: Int, status: Boolean) {
        viewModelScope.launch {
            todoUseCase.changeTodoStatus(id, status)
        }
    }
}