package app.loococo.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import app.loococo.presentation.theme.Gray2
import app.loococo.presentation.theme.White

@Composable
fun DoItTodoPopup(
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

            DoItBodyText(
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
            DoItBodyText(
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
fun rememberShowPopupState(
    initialValue: Boolean = false
): ShowPopupState {
    return remember { ShowPopupState(initialValue) }
}

@Stable
class ShowPopupState(initialValue: Boolean) {
    var showPopupState by mutableStateOf(initialValue)

    fun showPopup(){
        showPopupState = true
    }

    fun dismissPopup() {
        showPopupState = false
    }
}