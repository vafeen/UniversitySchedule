package ru.vafeen.universityschedule.presentation.components.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import ru.vafeen.universityschedule.domain.usecase.network.GetLatestReleaseUseCase
import ru.vafeen.universityschedule.presentation.utils.Link
import ru.vafeen.universityschedule.presentation.utils.copyTextToClipBoard
import kotlin.system.exitProcess


internal class MainActivityViewModel(
    val getLatestReleaseUseCase: GetLatestReleaseUseCase,
) : ViewModel() {
    var updateIsShowed = false
    fun registerGeneralExceptionCallback(context: Context) {
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            context.copyTextToClipBoard(
                label = "Error",
                text = "Contact us about this problem: ${Link.EMAIL}\n\n Exception in ${thread.name} thread\n${throwable.stackTraceToString()}"
            )
            Log.e("GeneralException", "Exception in thread ${thread.name}", throwable)
            exitProcess(0)
        }
    }
}