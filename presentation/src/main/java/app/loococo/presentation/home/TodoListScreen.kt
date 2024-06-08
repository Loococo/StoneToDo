package app.loococo.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import app.loococo.domain.model.Todo
import app.loococo.presentation.R
import app.loococo.presentation.component.DoItIconButton
import app.loococo.presentation.component.DoItTitleText
import app.loococo.presentation.theme.Black
import app.loococo.presentation.theme.Gray2
import app.loococo.presentation.utils.DoItIcons

@Composable
fun TodoListScreen(
    list: List<Todo>,
    onCheckedChange: (Int, Boolean) -> Unit,
    onShowRequest: (Todo) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(list) { todo ->
            TodoItem(
                todo,
                onCheckedChange,
                onShowRequest
            )
        }
    }
}

@Composable
fun TodoItem(
    item: Todo,
    onCheckedChange: (Int, Boolean) -> Unit,
    onShowRequest: (Todo) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomCheckbox(
                checked = item.status,
                onCheckedChange = { newChecked ->
                    onCheckedChange(item.id, newChecked)
                },
                interactionSource = interactionSource
            )
            Spacer(modifier = Modifier.width(7.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        onClick = {
                            onCheckedChange(item.id, !item.status)
                        },
                        interactionSource = interactionSource,
                        indication = null
                    )
            ) {
                DoItTitleText(
                    text = item.description,
                    textDecoration = if (item.status) TextDecoration.LineThrough else TextDecoration.None,
                    color = if (item.status) Gray2 else Black
                )
            }
            DoItIconButton(
                size = 20.dp,
                icon = DoItIcons.More,
                description = "더 보기",
                onClick = {
                    onShowRequest.invoke(item)
                }
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
    }
}

@Composable
fun CustomCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    interactionSource: MutableInteractionSource
) {
    Box(
        modifier = Modifier
            .size(23.dp)
            .clip(RoundedCornerShape(5.dp))
            .clickable(
                onClick = {
                    onCheckedChange(!checked)
                },
                interactionSource = interactionSource,
                indication = rememberRipple(bounded = true)
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = if (checked) painterResource(id = R.drawable.doit_icon2) else painterResource(
                id = R.drawable.doit_icon
            ),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
    }
}