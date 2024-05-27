package app.loococo.domain.model

sealed interface CalendarNavigation {
    data object None : CalendarNavigation
    data object NavigateToPreviousPeriod : CalendarNavigation
    data object NavigateToNextPeriod : CalendarNavigation
}