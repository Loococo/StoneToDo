package app.loococo.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.loococo.presentation.component.DoItIconButton
import app.loococo.presentation.component.DoItLabelText
import app.loococo.presentation.theme.Black
import app.loococo.presentation.theme.Gray4
import app.loococo.presentation.theme.White
import app.loococo.presentation.utils.DoItIcons

@Composable
fun AddToDo(onAddTodo: (String) -> Unit) {
    var textState by remember { mutableStateOf("") }
    val maxLines = 3
    val lineHeight = 22.dp
    val maxHeight = lineHeight * maxLines

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Gray4)
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(White, shape = RoundedCornerShape(15.dp))
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
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (textState.isNotBlank()) {
                            onAddTodo.invoke(textState)
                        }
                        textState = ""
                    }
                )
            )
            if (textState.isNotBlank()) {
                DoItIconButton(
                    size = 25.dp,
                    icon = DoItIcons.Plus,
                    description = "done",
                    onClick = {
                        onAddTodo.invoke(textState)
                        textState = ""
                    }
                )
            }
        }
    }
}