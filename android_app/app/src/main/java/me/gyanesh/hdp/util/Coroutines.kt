package me.gyanesh.hdp.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

fun <T : Any> CoroutineScope.ioThenMain(
    work: suspend (() -> T),
    callback: ((T) -> Unit)
) = launch {
    withContext(Dispatchers.Main) {
        val data = withContext(Dispatchers.IO) {
            async { return@async work() }
        }.await()
        callback(data)
    }
}

fun CoroutineScope.io(work: suspend (() -> Unit)) =
    launch {
        withContext(Dispatchers.IO) {
            work()
        }
    }

fun <T : Any> ViewModel.ioThenMain(
    work: suspend (() -> T),
    callback: ((T) -> Unit)
) = viewModelScope.ioThenMain(work, callback)

fun ViewModel.io(work: suspend (() -> Unit)) = viewModelScope.io(work)