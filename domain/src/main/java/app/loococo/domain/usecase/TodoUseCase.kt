package app.loococo.domain.usecase

import app.loococo.domain.model.Todo
import app.loococo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class TodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    suspend fun insert(todo: Todo) {
        todoRepository.insert(todo)
    }

    fun getTodoList(date: LocalDate): Flow<List<Todo>> {
        return todoRepository.getTodoList(date)
    }

    fun getTodoList(startDate: LocalDate, endDate: LocalDate): Flow<List<Todo>> {
        return todoRepository.getTodoList(startDate, endDate)
    }
}