package app.loococo.data.repository

import app.loococo.data.local.database.dao.TodoDao
import app.loococo.data.local.database.model.toTodo
import app.loococo.data.local.database.model.toTodoEntity
import app.loococo.domain.model.Todo
import app.loococo.domain.repository.TodoRepository
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao
) : TodoRepository {
    override suspend fun insert(todo: Todo) {
        todoDao.insert(todo.toTodoEntity())
    }

    override suspend fun getTodoList(date: Long): List<Todo> {
        return todoDao.getItemsByDate(date).map { it.toTodo() }
    }

    override suspend fun getAll(): List<Todo> {
        return todoDao.getAll().map { it.toTodo() }
    }
}