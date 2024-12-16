package ru.vafeen.universityschedule.domain.models

import androidx.compose.ui.graphics.Color
import com.google.gson.Gson

/**
 * Класс, представляющий локальные настройки приложения.
 *
 * @property lightThemeColor Цвет темы для светлого режима (может быть null).
 * @property darkThemeColor Цвет темы для темного режима (может быть null).
 * @property subgroup Подгруппа, к которой относится пользователь (может быть null).
 * @property link Ссылка, используемая в приложении (может быть null).
 * @property isSelectedFrequencyCorrespondsToTheWeekNumbers Флаг, указывающий, соответствует ли выбранная частота номерам недель (может быть null).
 * @property lastDemonstratedVersion Последняя продемонстрированная версия приложения.
 * @property weekendCat Флаг, указывающий на наличие функции гифки котика на выходном дне (по умолчанию false).
 * @property catInSettings Флаг, указывающий на наличие функции гифки котика в настройках (по умолчанию false).
 * @property notesAboutLesson Флаг, указывающий на наличие функции заметок о паре (по умолчанию false).
 * @property notificationsAboutLesson Флаг, указывающий на наличие функции уведомлений о паре (по умолчанию true).
 * @property releaseBody Описание релиза.
 * @property isMigrationFromAlarmManagerToWorkManagerSuccessful Флаг, указывающий на успешность миграции от AlarmManager к WorkManager (по умолчанию false).
 */
data class Settings(
    var lightThemeColor: Color? = null,
    var darkThemeColor: Color? = null,
    var subgroup: String? = null,
    var link: String? = null,
    var isSelectedFrequencyCorrespondsToTheWeekNumbers: Boolean? = null,
    var lastDemonstratedVersion: Long = 1,
    var weekendCat: Boolean = false, // Гифка котика на выходном дне
    var catInSettings: Boolean = false, // Гифка котика в настройках
    var notesAboutLesson: Boolean = false,
    var notificationsAboutLesson: Boolean = true,
    var releaseBody: String = "",
    var isMigrationFromAlarmManagerToWorkManagerSuccessful: Boolean = false
) {
    /**
     * Преобразует объект [Settings] в строку формата JSON.
     *
     * @return Строка в формате JSON, представляющая объект [Settings].
     */
    fun toJsonString(): String = Gson().toJson(this)
}
