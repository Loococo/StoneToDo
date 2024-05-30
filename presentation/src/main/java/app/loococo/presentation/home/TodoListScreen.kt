package app.loococo.presentation.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.unit.dp
import app.loococo.domain.model.Todo
import app.loococo.presentation.utils.DoItIcons

@Composable
fun TodoListScreen(list: List<Todo>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(list) { todo ->
            TodoItem(todo)
        }
    }
}

@Composable
fun TodoItem(item: Todo) {
    var checked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(20.dp, 0.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.Top
        ) {
            CustomCheckbox(checked = checked, onCheckedChange = { checked = it })
            Spacer(modifier = Modifier.width(7.dp))
            Text(
                text = item.description,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.height(7.dp))
    }
}

@Composable
fun CustomCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Column {
        Spacer(modifier = Modifier.height(3.dp))
        Box(
            modifier = Modifier
                .size(18.dp)
                .background(
                    color = if (checked) Color.LightGray else Color.Transparent,
                    shape = RoundedCornerShape(4.dp)
                )
                .border(
                    border = BorderStroke(2.dp, if (checked) Color.LightGray else Color.LightGray),
                    shape = RoundedCornerShape(4.dp)
                )
                .clickable { onCheckedChange(!checked) },
            contentAlignment = Alignment.Center
        ) {
            if (checked) {
                Icon(
                    imageVector = DoItIcons.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}