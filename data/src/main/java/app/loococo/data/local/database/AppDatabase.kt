package app.loococo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import app.loococo.data.local.database.dao.TodoDao
import app.loococo.data.local.database.model.TodoEntity

@Database(entities = [TodoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}