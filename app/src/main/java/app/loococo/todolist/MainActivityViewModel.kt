package app.loococo.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {
    val uiState: StateFlow<MainActivityUiState> = flow {
        emit(MainActivityUiState.Loading)
        delay(1000)
        emit(MainActivityUiState.Success)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), MainActivityUiState.Loading)
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data object Success : MainActivityUiState
}
