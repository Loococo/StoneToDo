package app.loococo.domain.usecase

import app.loococo.domain.model.Todo
import app.loococo.domain.repository.TodoRepository
import javax.inject.Inject

class TodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    suspend fun insert(todo: Todo) {
        todoRepository.insert(todo)
    }

    suspend fun getTodoList(date: Long): List<Todo> {
        return todoRepository.getTodoList(date)
    }

    suspend fun getAll(): List<Todo> {
        return todoRepository.getAll()
    }
}