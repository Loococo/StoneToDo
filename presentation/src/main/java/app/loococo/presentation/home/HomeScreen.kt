package app.loococo.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.loococo.presentation.utils.DoItIcons

@Composable
internal fun HomeRoute() {
    HomeScreen()
}

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = hiltViewModel()
    val currentDayState by viewModel.currentDay.collectAsStateWithLifecycle()
    val calendarTypeState by viewModel.calendarType.collectAsStateWithLifecycle()
    val selectedDateState by viewModel.selectedDate.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CalendarScreen(
            currentDay = currentDayState,
            calendarType = calendarTypeState,
            selectedDate = selectedDateState,
            onCalendarNavigation = viewModel::onCalendarNavigation,
            onCalendarTypeChange = viewModel::onCalendarTypeChange,
            onDateSelected = viewModel::updateSelectedDate
        )
        Spacer(modifier = Modifier.height(5.dp))
        Box(modifier = Modifier.weight(1f)) {

        }
        AddToDo(onAddTodo = viewModel::insert)
    }
}

@Composable
fun AddToDo(onAddTodo: (String) -> Unit) {
    var textState by remember { mutableStateOf(TextFieldValue("")) }
    val maxLines = 3
    val lineHeight = 22.dp
    val maxHeight = lineHeight * maxLines

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray, shape = RoundedCornerShape(15.dp))
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
                    color = Color.Black,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Default
                ),
                cursorBrush = SolidColor(Color.Black),
                decorationBox = { innerTextField ->
                    if (textState.text.isEmpty()) {
                        Text(
                            text = "할 일을 적어주세요.",
                            style = TextStyle(
                                color = Color.Gray,
                                fontWeight = FontWeight.Normal,
                                fontFamily = FontFamily.Default
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
                        if (textState.text.isNotBlank()) {
                            onAddTodo.invoke(textState.text)
                        }
                    }
                )
            )
            if (textState.text.isNotBlank()) {
                IconButton(
                    onClick = { onAddTodo.invoke(textState.text) },
                    modifier = Modifier.size(25.dp)
                ) {
                    Icon(DoItIcons.Plus, contentDescription = "done")
                }
            }
        }
    }
}