package app.loococo.data.repository

import app.loococo.data.local.database.dao.TodoDao
import app.loococo.data.local.database.model.toISO
import app.loococo.data.local.database.model.toTodo
import app.loococo.data.local.database.model.toTodoEntity
import app.loococo.domain.model.Todo
import app.loococo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao
) : TodoRepository {
    override suspend fun insert(todo: Todo) {
        todoDao.insert(todo.toTodoEntity())
    }

    override fun getTodoList(date: LocalDate): Flow<List<Todo>> {
        return todoDao.getItemsByDate(date.toISO()).map { databaseTodos ->
            databaseTodos.map { it.toTodo() }
        }
    }

    override fun getTodoList(startDate: LocalDate, endDate: LocalDate): Flow<List<Todo>> {
        return todoDao.getItemsBetweenDates(startDate.toISO(), endDate.toISO())
            .map { databaseTodos ->
                databaseTodos.map { it.toTodo() }
            }
    }

    override suspend fun changeTodoStatus(id: Int, status: Boolean) {
        todoDao.changeTodoStatus(id, status)
    }

    override suspend fun changeTodoDescription(id: Int, description: String) {
        todoDao.changeTodoDescription(id, description)
    }

    override suspend fun deleteTodo(id: Int) {
        todoDao.deleteById(id)
    }
}