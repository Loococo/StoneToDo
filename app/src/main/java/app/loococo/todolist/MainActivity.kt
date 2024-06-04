package app.loococo.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import app.loococo.todolist.navigation.DoItApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()

        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                splashScreen.setKeepOnScreenCondition { uiState == MainActivityUiState.Loading }
            }
        }

        setContent {
            DoItApp()
        }
    }
}