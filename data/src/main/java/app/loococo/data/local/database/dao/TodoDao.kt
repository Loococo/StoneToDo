package app.loococo.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.loococo.data.local.database.model.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert
    suspend fun insert(todoEntity: TodoEntity)

    @Query("SELECT * FROM todo WHERE date = :date")
    fun getItemsByDate(date: String): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todo WHERE date BETWEEN :startDate AND :endDate")
    fun getItemsBetweenDates(startDate: String, endDate: String): Flow<List<TodoEntity>>
}