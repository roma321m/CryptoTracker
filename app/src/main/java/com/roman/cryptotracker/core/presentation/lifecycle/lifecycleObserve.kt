package com.roman.cryptotracker.core.presentation.lifecycle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Composable
fun Lifecycle.Observe(callback: (Lifecycle.Event) -> Unit) {
    DisposableEffect(this) {
        val observer = LifecycleEventObserver { _, event ->
            callback(event)
        }
        this@Observe.addObserver(observer)
        onDispose {
            this@Observe.removeObserver(observer)
        }
    }
}