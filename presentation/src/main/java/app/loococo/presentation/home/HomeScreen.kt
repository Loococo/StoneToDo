package app.loococo.presentation.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.loococo.presentation.calendar.TodoListScreen
import app.loococo.presentation.component.HeightSpacer
import app.loococo.presentation.component.StoneToDoAddPopup
import app.loococo.presentation.component.StoneToDoIconButton
import app.loococo.presentation.component.StoneToDoLabelText
import app.loococo.presentation.component.StoneToDoTitleText
import app.loococo.presentation.component.WidthSpacer
import app.loococo.presentation.component.rememberShowAddPopupState
import app.loococo.presentation.theme.Gray1
import app.loococo.presentation.theme.Gray2
import app.loococo.presentation.theme.Green
import app.loococo.presentation.utils.StoneToDoIcons

@Composable
internal fun HomeRoute() {
    HomeScreen()
}


@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = hiltViewModel()

    val todoListState by viewModel.todoList.collectAsStateWithLifecycle()
    val todoStats by viewModel.todoStats.collectAsStateWithLifecycle()

    val showPopupState = rememberShowAddPopupState()

    if (showPopupState.showPopupState) {
        StoneToDoAddPopup(
            todo = showPopupState.selectedTodoState,
            onAddTodo = viewModel::insert,
            onEditTodo = viewModel::changeTodoDescription,
            onDismissRequest = showPopupState::dismissPopup
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(vertical = 30.dp, horizontal = 20.dp)) {
            HomeHeader("이번 달 완료 투두")
            TodoBarGraph(todoStats)

            HeightSpacer(height = 40)

            HomeHeader("오늘의 투두")
            TodoListScreen(
                list = todoListState,
                onCheckedChange = viewModel::changeTodoStatus,
                onChangeTodoDescription = {
                    showPopupState.showPopup(it)
                },
                onDeleteTodo = viewModel::deleteTodo
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {
            StoneToDoIconButton(
                size = 60.dp,
                icon = StoneToDoIcons.Plus,
                description = "add",
                onClick = showPopupState::showPopup
            )
        }
    }
}

@Composable
fun HomeHeader(title: String) {
    StoneToDoTitleText(text = title, fontWeight = FontWeight.Bold)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        HeightSpacer(Modifier.fillMaxWidth(), 1, Gray2)
    }
}

@Composable
fun TodoBarGraph(todoStats: TodoStats) {
    Column {
        Row {
            StoneToDoLabelText(text = "총 : ${todoStats.total}")
            WidthSpacer(width = 5)
            StoneToDoLabelText(text = "완료 : ${todoStats.completed}")
        }
        HeightSpacer(height = 5)
        TodoBarGraphContent(todoStats)
    }
}

@Composable
fun TodoBarGraphContent(todoStats: TodoStats) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
    ) {
        val total = todoStats.total.toFloat()
        if (total > 0) {
            val completedPercentage = todoStats.completed / total
            val pendingPercentage = todoStats.pending / total

            if (completedPercentage > 0) {
                Canvas(
                    modifier = Modifier
                        .weight(completedPercentage)
                        .fillMaxHeight()
                ) {
                    drawRoundRect(
                        color = Green
                    )
                }
            }

            if (pendingPercentage > 0) {
                Canvas(
                    modifier = Modifier
                        .weight(pendingPercentage)
                        .fillMaxHeight()
                ) {
                    drawRoundRect(
                        color = Gray1
                    )
                }
            }
        } else {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                drawRoundRect(
                    color = Gray1
                )
            }
        }
    }
}