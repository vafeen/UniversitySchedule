package ru.vafeen.universityschedule.domain.usecase

import android.content.Context
import android.widget.Toast
import ru.vafeen.universityschedule.domain.usecase.base.UseCase
import ru.vafeen.universityschedule.resources.R

/**
 * UseCase для отображения сообщения "Мяу".
 *
 * Этот класс отвечает за отображение тоста с текстом "Мяу" при вызове.
 *
 * @property context Контекст приложения, необходимый для отображения тоста.
 */
class CatMeowUseCase(private val context: Context) : UseCase {

    /**
     * Отображает тост с сообщением "Мяу".
     */
    operator fun invoke() {
        Toast.makeText(context, context.getString(R.string.meow), Toast.LENGTH_SHORT).show()
    }
}
