package ru.vafeen.universityschedule.domain

import android.content.Intent

/**
 * Интерфейс для предоставления интента для главной активности.
 *
 * Этот интерфейс определяет метод для создания и предоставления
 * интента, который может быть использован для запуска главной активности приложения.
 */
interface MainActivityIntentProvider {

    /**
     * Предоставляет интент для запуска главной активности.
     *
     * @return Интент [Intent], который можно использовать для запуска активности.
     */
    fun provideIntent(): Intent
}
