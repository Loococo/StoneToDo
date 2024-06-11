package app.loococo.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.loococo.domain.model.CalendarNavigation
import app.loococo.domain.model.CalendarType
import app.loococo.domain.model.Todo
import app.loococo.domain.usecase.PreferencesUseCase
import app.loococo.domain.usecase.TodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
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

    private val currentLocalData by lazy {
        LocalDate.now()
    }

    private val _calendarTypeFlow: MutableStateFlow<CalendarType> =
        MutableStateFlow(preferencesUseCase.getCalendarType())
    val calendarType: StateFlow<CalendarType> = _calendarTypeFlow

    private val _selectedDateFlow: MutableStateFlow<LocalDate> = MutableStateFlow(currentLocalData)
    val selectedDate: StateFlow<LocalDate> = _selectedDateFlow

    private val _dateRangeFlow: MutableStateFlow<Pair<LocalDate, LocalDate>> =
        MutableStateFlow(Pair(currentLocalData, currentLocalData))

    @OptIn(ExperimentalCoroutinesApi::class)
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

    val todoList: StateFlow<List<Todo>> =
        combine(_selectedDateFlow, todoListMap) { selectedDate, todoMap ->
            todoMap[selectedDate] ?: emptyList()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun onCalendarNavigation(navigation: CalendarNavigation) {
        val currentDayValue = _selectedDateFlow.value
        when (_calendarTypeFlow.value) {
            CalendarType.DayOfMonth -> {
                when (navigation) {
                    CalendarNavigation.NavigateToNextPeriod -> {
                        updateSelectedDate(currentDayValue.plusMonths(1).withDayOfMonth(1))
                    }

                    CalendarNavigation.NavigateToPreviousPeriod -> {
                        updateSelectedDate(currentDayValue.minusMonths(1).withDayOfMonth(1))
                    }

                    else -> return
                }
            }

            CalendarType.DayOfWeek -> {
                when (navigation) {
                    CalendarNavigation.NavigateToNextPeriod -> {
                        updateSelectedDate(
                            currentDayValue.plusWeeks(1)
                                .with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
                        )
                    }

                    CalendarNavigation.NavigateToPreviousPeriod -> {
                        updateSelectedDate(
                            currentDayValue.minusWeeks(1).with(
                                TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)
                            )
                        )
                    }

                    else -> return
                }
            }
        }
    }

    fun onCalendarTypeChange() {
        val currentDayValue = _selectedDateFlow.value
        _calendarTypeFlow.value = when (_calendarTypeFlow.value) {
            CalendarType.DayOfMonth -> {
                updateSelectedDate(
                    currentDayValue
                )
                CalendarType.DayOfWeek
            }

            CalendarType.DayOfWeek -> {
                updateSelectedDate(currentDayValue)
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

    fun changeTodoDescription(todo: Todo) {
        viewModelScope.launch {
            todoUseCase.changeTodoDescription(todo.id, todo.description)
        }
    }

    fun deleteTodo(todo: Todo?) {
        todo?.let {
            viewModelScope.launch {
                todoUseCase.deleteTodo(it.id)
            }
        }
    }
}