package app.loococo.domain.model

sealed interface CalendarType {
    data object DayOfMonth : CalendarType
    data object DayOfWeek : CalendarType
}

fun CalendarType.toStringValue(): String {
    return when (this) {
        CalendarType.DayOfMonth -> "DayOfMonth"
        CalendarType.DayOfWeek -> "DayOfWeek"
    }
}

fun String?.toCalendarType(): CalendarType {
    return when (this) {
        "DayOfMonth" -> CalendarType.DayOfMonth
        "DayOfWeek" -> CalendarType.DayOfWeek
        else -> CalendarType.DayOfWeek
    }
}