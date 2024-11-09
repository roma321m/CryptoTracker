package com.roman.cryptotracker.core.presentation.lifecycle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


@Composable
fun <E> ObserveAsEvents(
    events: Flow<E>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent: (E) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner.lifecycle, key1, key2) {
        withContext(Dispatchers.Main.immediate) {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                events.collect(onEvent)
            }
        }
    }
}