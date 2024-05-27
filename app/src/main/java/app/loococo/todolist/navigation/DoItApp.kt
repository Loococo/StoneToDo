package app.loococo.todolist.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.loococo.todolist.ui.theme.DoItTheme

@Composable
fun DoItApp() {
    DoItTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            DoItNavHost()
        }
    }
}