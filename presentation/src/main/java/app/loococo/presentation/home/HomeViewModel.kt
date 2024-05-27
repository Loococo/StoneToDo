package app.loococo.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.loococo.domain.model.CalendarNavigation
import app.loococo.domain.model.CalendarType
import app.loococo.presentation.ext.stateInWhileScribe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : ViewModel() {

    private val _currentMonth: MutableStateFlow<LocalDate> = MutableStateFlow(LocalDate.now())
    val currentMonth: StateFlow<LocalDate> =
        _currentMonth.stateInWhileScribe(viewModelScope, LocalDate.now())

    private val _calendarNavigation: MutableStateFlow<CalendarNavigation> =
        MutableStateFlow(CalendarNavigation.None)
    val calendarNavigation: StateFlow<CalendarNavigation> =
        _calendarNavigation.stateInWhileScribe(viewModelScope, CalendarNavigation.None)

    private val _calendarType: MutableStateFlow<CalendarType> =
        MutableStateFlow(CalendarType.DayOfWeek)
    val calendarType: StateFlow<CalendarType> =
        _calendarType.stateInWhileScribe(viewModelScope, CalendarType.DayOfWeek)

    fun onCalendarNavigation(navigation: CalendarNavigation) {
        val currentMonth = _currentMonth.value
        val updatedMonth = when (_calendarType.value) {
            CalendarType.DayOfMonth -> {
                when (navigation) {
                    CalendarNavigation.NavigateToNextPeriod -> currentMonth.plusMonths(1)
                    CalendarNavigation.NavigateToPreviousPeriod -> currentMonth.minusMonths(1)
                    else -> null
                }
            }

            CalendarType.DayOfWeek -> {
                when (navigation) {
                    CalendarNavigation.NavigateToNextPeriod -> currentMonth.plusWeeks(1)
                    CalendarNavigation.NavigateToPreviousPeriod -> currentMonth.minusWeeks(1)
                    else -> null
                }
            }
        }
        updatedMonth?.let {
            _currentMonth.value = it
        }
    }

    fun onCalendarTypeChange() {
        when (_calendarType.value) {
            CalendarType.DayOfMonth -> _calendarType.value = CalendarType.DayOfWeek
            CalendarType.DayOfWeek -> _calendarType.value = CalendarType.DayOfMonth
        }
    }
}