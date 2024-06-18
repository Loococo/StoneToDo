package app.loococo.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import app.loococo.domain.model.Todo
import app.loococo.presentation.theme.Black
import app.loococo.presentation.theme.Gray2
import app.loococo.presentation.theme.Gray4
import app.loococo.presentation.theme.White
import app.loococo.presentation.utils.StoneToDoIcons

@Composable
fun StoneToDoAddPopup(
    todo: Todo?,
    onAddTodo: (String) -> Unit,
    onEditTodo: (Todo) -> Unit,
    onDismissRequest: () -> Unit
) {
    val todoEditStatus = todo != null
    var textState by remember {
        mutableStateOf(
            if (todoEditStatus) TextFieldValue(
                todo!!.description,
                TextRange(todo.description.length)
            )
            else TextFieldValue("")
        )
    }
    val maxLength = 50
    val maxLines = 5

    val focusRequester = remember { FocusRequester() }

    Dialog(
        onDismissRequest = { onDismissRequest.invoke() }
    ) {
        Column(
            modifier = Modifier
                .background(White, RoundedCornerShape(10.dp))
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StoneToDoLabelText(text = "할 일 추가하기")
                HeightSpacer(height = 10)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(White, RoundedCornerShape(10.dp))
                        .border(1.dp, Black, RoundedCornerShape(10.dp))
                        .padding(10.dp, 3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }

                    BasicTextField(
                        value = textState,
                        onValueChange = { newTextState ->
                            if (newTextState.text.length <= maxLength) textState = newTextState
                        },
                        maxLines = maxLines,
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp)
                            .focusRequester(focusRequester),
                        textStyle = TextStyle(
                            color = Black,
                        ),
                        cursorBrush = SolidColor(Black),
                        decorationBox = { innerTextField ->
                            if (textState.text.isEmpty()) {
                                Text(
                                    text = "할 일을 적어주세요.",
                                    style = TextStyle(
                                        color = Gray4,
                                    )
                                )
                            }
                            innerTextField()
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Text
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if (textState.text.isNotBlank()) {
                                    if (todoEditStatus) {
                                        onEditTodo.invoke(todo!!.copy(description = textState.text))
                                    } else {
                                        onAddTodo.invoke(textState.text)
                                    }
                                    onDismissRequest.invoke()
                                }
                                textState = TextFieldValue("")
                            }
                        )
                    )
                    if (textState.text.isNotBlank()) {
                        StoneToDoIconButton(
                            size = 25.dp,
                            icon = if (todoEditStatus) StoneToDoIcons.Edit else StoneToDoIcons.Plus,
                            description = "done",
                            onClick = {
                                if (todoEditStatus) {
                                    onEditTodo.invoke(todo!!.copy(description = textState.text))
                                } else {
                                    onAddTodo.invoke(textState.text)
                                }
                                onDismissRequest.invoke()
                                textState = TextFieldValue("")
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun StoneToDoOptionPopup(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest.invoke() }
    ) {
        Column(
            modifier = Modifier
                .background(White, RoundedCornerShape(10.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.clickable {
                onDismissRequest.invoke()
                onEditClick.invoke()
            }) {
                StoneToDoBodyText(
                    text = "수정하기",
                    modifier = Modifier
                        .padding(50.dp, 20.dp)
                )
            }
            WidthHeightSpacer(width = 120, height = 1, backgroundColor = Gray2)
            Box(modifier = Modifier.clickable {
                onDismissRequest.invoke()
                onDeleteClick.invoke()
            }) {
                StoneToDoBodyText(
                    text = "삭제하기",
                    modifier = Modifier
                        .padding(50.dp, 20.dp)
                )
            }
        }
    }
}

@Composable
fun rememberShowAddPopupState(
    initialValue: Boolean = false,
    selectedTodo: Todo? = null
): ShowPopupState {
    return remember { ShowPopupState(initialValue, selectedTodo) }
}


@Composable
fun rememberShowOptionPopupState(
    initialValue: Boolean = false,
    selectedTodo: Todo? = null
): ShowPopupState {
    return remember { ShowPopupState(initialValue, selectedTodo) }
}

@Stable
class ShowPopupState(initialValue: Boolean, selectedTodo: Todo?) {
    var showPopupState by mutableStateOf(initialValue)
    var selectedTodoState by mutableStateOf(selectedTodo)

    fun showPopup(todo: Todo? = null) {
        showPopupState = true
        selectedTodoState = todo
    }

    fun dismissPopup() {
        showPopupState = false
    }
}