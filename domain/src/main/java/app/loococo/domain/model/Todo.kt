package app.loococo.domain.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Todo(
    val date: LocalDate,
    val description: String,
    val completion: Boolean = false
)