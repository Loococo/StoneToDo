package app.loococo.domain.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Todo(
    val id: Int = 0,
    val date: LocalDate,
    val description: String,
    val status: Boolean = false
)