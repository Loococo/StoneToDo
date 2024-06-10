package app.loococo.stonetodo.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.loococo.presentation.theme.StoneToDoTheme
import app.loococo.presentation.theme.White

@Composable
fun StoneToDo() {
    StoneToDoTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = White
        ) {
            StoneToDoNavHost()
        }
    }
}
