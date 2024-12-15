package ru.vafeen.universityschedule.domain.usecase

import android.content.Context
import android.widget.Toast
import ru.vafeen.universityschedule.domain.usecase.base.UseCase
import ru.vafeen.universityschedule.resources.R

class CatMeowUseCase(private val context: Context) : UseCase {
    operator fun invoke() {
        Toast.makeText(context, context.getString(R.string.meow), Toast.LENGTH_SHORT).show()
    }
}