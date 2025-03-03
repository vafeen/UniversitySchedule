package ru.vafeen.universityschedule.domain.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun CoroutineScope.launchIO(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = launch(Dispatchers.IO, start, block)

fun CoroutineScope.launchMain(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = launch(Dispatchers.Main, start, block)

suspend fun <T> withContextIO(
    block: suspend CoroutineScope.() -> T
) = withContext(Dispatchers.IO, block)

suspend fun <T> withContextMain(
    block: suspend CoroutineScope.() -> T
) = withContext(Dispatchers.Main, block)

