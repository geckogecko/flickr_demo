package at.steinbacher.common

import kotlinx.coroutines.CoroutineDispatcher

class Dispatchers(
    val io: CoroutineDispatcher = kotlinx.coroutines.Dispatchers.IO,
    val main: CoroutineDispatcher = kotlinx.coroutines.Dispatchers.Main
)