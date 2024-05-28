package app.loococo.presentation.home

import androidx.lifecycle.ViewModel
import app.loococo.domain.model.CalendarNavigation
import app.loococo.domain.model.CalendarType
import app.loococo.domain.usecase.PreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val preferencesUseCase: PreferencesUseCase
) : ViewModel() {

    private val _currentDayFlow: MutableStateFlow<LocalDate> = MutableStateFlow(LocalDate.now())
    val currentDay: StateFlow<LocalDate> = _currentDayFlow

    private val _calendarTypeFlow: MutableStateFlow<CalendarType> =
        MutableStateFlow(preferencesUseCase.getCalendarType())
    val calendarType: StateFlow<CalendarType> = _calendarTypeFlow

    private val _selectedDateFlow: MutableStateFlow<LocalDate> = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDateFlow

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
}
