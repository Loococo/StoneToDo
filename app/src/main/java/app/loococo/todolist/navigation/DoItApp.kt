package app.loococo.todolist.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.loococo.presentation.theme.DoItTheme
import app.loococo.presentation.theme.White

@Composable
fun DoItApp() {
    DoItTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = White
        ) {
            DoItNavHost()
        }
    }
}