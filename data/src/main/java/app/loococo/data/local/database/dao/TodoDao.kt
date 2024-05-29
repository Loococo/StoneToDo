package app.loococo.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.loococo.data.local.database.model.TodoEntity

@Dao
interface TodoDao {
    @Insert
    suspend fun insert(todoEntity: TodoEntity)

    @Query("SELECT * FROM todo WHERE date = :date")
    suspend fun getItemsByDate(date: Long): List<TodoEntity>

    @Query("SELECT * FROM todo")
    suspend fun getAll(): List<TodoEntity>
}