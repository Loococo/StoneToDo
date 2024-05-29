package app.loococo.domain.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Todo(
    val date: String,
    val description: String,
    val completion: Boolean = false
)

fun LocalDate.toISO(): String {
    return this.format(DateTimeFormatter.ISO_LOCAL_DATE)
}

fun String.toLocalDate(): LocalDate {
    return LocalDate.parse(this, DateTimeFormatter.ISO_LOCAL_DATE)
}