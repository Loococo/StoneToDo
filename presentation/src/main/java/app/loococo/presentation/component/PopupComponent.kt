package app.loococo.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
    onAddTodo: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    var textState by remember { mutableStateOf("") }

    val maxLines = 3
    val lineHeight = 25.dp
    val maxHeight = lineHeight * maxLines

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
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(White, RoundedCornerShape(10.dp))
                        .border(1.dp, Black, RoundedCornerShape(10.dp))
                        .padding(10.dp, 3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = textState,
                        onValueChange = { textState = it },
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = lineHeight, max = maxHeight)
                            .padding(10.dp),
                        textStyle = TextStyle(
                            color = Black,
                        ),
                        cursorBrush = SolidColor(Black),
                        decorationBox = { innerTextField ->
                            if (textState.isEmpty()) {
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
                                if (textState.isNotBlank()) {
                                    onAddTodo.invoke(textState)
                                    onDismissRequest.invoke()
                                }
                                textState = ""
                            }
                        )
                    )
                    if (textState.isNotBlank()) {
                        StoneToDoIconButton(
                            size = 25.dp,
                            icon = StoneToDoIcons.Plus,
                            description = "done",
                            onClick = {
                                onAddTodo.invoke(textState)
                                onDismissRequest.invoke()
                                textState = ""
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
                .background(White, RoundedCornerShape(10.dp))
                .padding(20.dp, 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            StoneToDoBodyText(
                text = "수정하기",
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        onDismissRequest.invoke()
                        onEditClick.invoke()
                    }
            )
            Spacer(
                modifier = Modifier
                    .width(120.dp)
                    .height(1.dp)
                    .background(Gray2)
            )
            StoneToDoBodyText(
                text = "삭제하기",
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        onDismissRequest.invoke()
                        onDeleteClick.invoke()
                    }
            )
        }
    }
}

@Composable
fun rememberShowAddPopupState(
    initialValue: Boolean = false
): ShowAddPopupState {
    return remember { ShowAddPopupState(initialValue) }
}

@Stable
class ShowAddPopupState(initialValue: Boolean) {
    var showPopupState by mutableStateOf(initialValue)

    fun showPopup() {
        showPopupState = true
    }

    fun dismissPopup() {
        showPopupState = false
    }
}


@Composable
fun rememberShowOptionPopupState(
    initialValue: Boolean = false,
    selectedTodo: Todo? = null
): ShowOptionPopupState {
    return remember { ShowOptionPopupState(initialValue, selectedTodo) }
}

@Stable
class ShowOptionPopupState(initialValue: Boolean, selectedTodo: Todo?) {
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