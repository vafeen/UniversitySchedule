package ru.vafeen.universityschedule.domain.utils

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.vafeen.universityschedule.domain.models.Settings

/**
 * Сохраняет данные в SharedPreferences или удаляет их, в зависимости от переданного блока.
 *
 * Эта функция позволяет выполнить операции редактирования SharedPreferences,
 * используя переданный блок кода.
 *
 * @param save Блок кода, который будет выполнен в контексте [SharedPreferences.Editor].
 */
fun SharedPreferences.saveInOrRemoveFromSharedPreferences(save: SharedPreferences.Editor.() -> Unit) {
    edit().apply {
        save() // Выполнение переданного блока.
        apply() // Применение изменений.
    }
}

/**
 * Получает данные из SharedPreferences с использованием переданного блока.
 *
 * @param get Блок кода, который будет выполнен в контексте [SharedPreferences].
 * @return Результат выполнения блока.
 */
fun <T> SharedPreferences.getFromSharedPreferences(get: SharedPreferences.() -> T): T = get()

/**
 * Получает настройки из SharedPreferences или создает новые, если их нет.
 *
 * Эта функция проверяет наличие настроек в SharedPreferences и возвращает их.
 * Если настройки не найдены, создаются новые и сохраняются в SharedPreferences.
 *
 * @return Объект [Settings], полученный из SharedPreferences или созданный по умолчанию.
 */
fun SharedPreferences.getSettingsOrCreateIfNull(): Settings {
    val settings = getFromSharedPreferences {
        getString(SharedPreferencesValue.Settings.key, "").let {
            if (it != "") Gson().fromJson(it, Settings::class.java)
            else null
        }
    }
    return if (settings != null) settings
    else {
        val newSettings = Settings() // Создание новых настроек по умолчанию.
        saveInOrRemoveFromSharedPreferences {
            putString(SharedPreferencesValue.Settings.key, newSettings.toJsonString())
        }
        newSettings
    }
}

/**
 * Сохраняет настройки в SharedPreferences.
 *
 * @param settings Объект [Settings], который нужно сохранить.
 */
fun SharedPreferences.save(settings: Settings) = saveInOrRemoveFromSharedPreferences {
    putString(SharedPreferencesValue.Settings.key, settings.toJsonString())
}
