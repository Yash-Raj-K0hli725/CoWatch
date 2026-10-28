package com.cranoxz.streamroom.util.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@OptIn(FlowPreview::class)
@Composable
fun <T> StateFlow<T>.collectLatestStateWithLifecycle(
    initialValue: T,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext
): State<T> {
    return produceState(initialValue, this, lifecycleOwner, minActiveState, context) {
        lifecycleOwner.repeatOnLifecycle(minActiveState) {
            if (context == EmptyCoroutineContext) {
                this@collectLatestStateWithLifecycle.debounce(16L).collectLatest { this@produceState.value = it }
            } else
                withContext(context) {
                    this@collectLatestStateWithLifecycle.collectLatest {
                        this@produceState.value = it
                    }
                }
        }
    }
}