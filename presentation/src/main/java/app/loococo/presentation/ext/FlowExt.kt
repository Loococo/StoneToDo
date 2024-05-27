package app.loococo.presentation.ext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

fun <T> Flow<T>.stateInWhileScribe(
    scope: CoroutineScope,
    initialValue: T,
    whileSubscribedTime: Long = 5_000
) = stateIn(scope, SharingStarted.WhileSubscribed(whileSubscribedTime), initialValue)
