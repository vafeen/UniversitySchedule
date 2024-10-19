package ru.vafeen.universityschedule.presentation.components.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import ru.vafeen.universityschedule.data.network.repository.NetworkRepository
import ru.vafeen.universityschedule.data.utils.copyTextToClipBoard
import ru.vafeen.universityschedule.presentation.utils.Link
import kotlin.system.exitProcess


internal class MainActivityViewModel(
    val networkRepository: NetworkRepository,
) : ViewModel() {
    var updateIsShowed = false
    fun registerGeneralExceptionCallback(context: Context) {
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            context.copyTextToClipBoard(
                label = "Error",
                text = "Contact us about this problem: ${Link.MAIL}\n\n Exception in ${thread.name} thread\n${throwable.stackTraceToString()}"
            )
            Log.e("GeneralException", "Exception in thread ${thread.name}", throwable)
            exitProcess(0)
        }
    }
}