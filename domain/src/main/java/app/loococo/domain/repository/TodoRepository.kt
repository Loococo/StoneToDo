package app.loococo.domain.repository

import app.loococo.domain.model.Todo

interface TodoRepository {

    suspend fun insert(todo: Todo)

    suspend fun getTodoList(date: Long): List<Todo>

    suspend fun getAll(): List<Todo>
}