package app.loococo.domain.model

sealed interface CalendarType {
    data object DayOfMonth : CalendarType
    data object DayOfWeek : CalendarType
}