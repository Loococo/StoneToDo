package app.loococo.data.local.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import app.loococo.domain.model.Todo

@Entity(tableName = "todo")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val description: String,
    val completion: Boolean = false
)


fun TodoEntity.toTodo(): Todo {
    return Todo(
        date = date,
        description = description,
        completion = completion
    )
}

fun Todo.toTodoEntity(): TodoEntity {
    return TodoEntity(
        date = date,
        description = description,
        completion = completion
    )
}