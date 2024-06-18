package app.loococo.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.loococo.domain.model.Todo
import app.loococo.domain.usecase.TodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val todoUseCase: TodoUseCase
) : ViewModel() {

    private val currentLocalData by lazy {
        LocalDate.now()
    }

    val todoListMap: StateFlow<Map<LocalDate, List<Todo>>> get() = _todoListMap
    private val _todoListMap: StateFlow<Map<LocalDate, List<Todo>>> =
        todoUseCase.getTodoList(
            currentLocalData.withDayOfMonth(1),
            currentLocalData.with(TemporalAdjusters.lastDayOfMonth())
        )
            .onStart { emit(emptyList()) }
            .map { todos ->
                todos.groupBy { it.date }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyMap()
            )


    val todoStats: StateFlow<TodoStats> = _todoListMap
        .map { todoMap ->
            val total = todoMap.values.flatten().size
            val completed = todoMap.values.flatten().count { it.status }
            val pending = total - completed
            TodoStats(total, completed, pending)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TodoStats(0, 0, 0)
        )

    val todoList: StateFlow<List<Todo>> = _todoListMap
        .map { todoMap ->
            val list = todoMap[currentLocalData] ?: emptyList()
            list.sortedBy { it.status }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun insert(description: String) {
        val todo = Todo(date = currentLocalData, description = description)
        viewModelScope.launch {
            todoUseCase.insert(todo)
        }
    }

    fun changeTodoStatus(id: Int, status: Boolean) {
        viewModelScope.launch {
            todoUseCase.changeTodoStatus(id, status)
        }
    }

    fun changeTodoDescription(todo: Todo) {
        viewModelScope.launch {
            todoUseCase.changeTodoDescription(todo.id, todo.description)
        }
    }

    fun deleteTodo(todo: Todo?) {
        todo?.let {
            viewModelScope.launch {
                todoUseCase.deleteTodo(it.id)
            }
        }
    }
}

data class TodoStats(
    val total: Int,
    val completed: Int,
    val pending: Int
)