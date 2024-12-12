package ru.vafeen.universityschedule.data.impl.network.service

import android.content.SharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.vafeen.universityschedule.domain.models.Settings
import ru.vafeen.universityschedule.domain.network.service.SettingsManager
import ru.vafeen.universityschedule.domain.utils.getSettingsOrCreateIfNull
import ru.vafeen.universityschedule.domain.utils.save

/**
 * Реализация интерфейса [SettingsManager] для управления настройками приложения с использованием [SharedPreferences].
 * Этот класс предоставляет реактивный способ наблюдения за изменениями настроек через [StateFlow].
 *
 * @property sharedPreferences Экземпляр [SharedPreferences], используемый для хранения и загрузки настроек.
 */
internal class SettingsManagerImpl(private val sharedPreferences: SharedPreferences) :
    SettingsManager {

    // Текущие настройки, загруженные из SharedPreferences
    private var settings = sharedPreferences.getSettingsOrCreateIfNull()

    // Внутренний StateFlow для отслеживания изменений настроек
    private val _settingsFlow = MutableStateFlow(settings)

    /**
     * Публичный [StateFlow] для подписки на изменения настроек.
     * С помощью этого потока можно получать уведомления об обновлениях настроек.
     */
    override val settingsFlow: StateFlow<Settings> = _settingsFlow.asStateFlow()

    /**
     * Регистрация слушателя для отслеживания изменений в [SharedPreferences].
     * При обнаружении изменения обновленные настройки отправляются в [StateFlow].
     */
    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
            CoroutineScope(Dispatchers.IO).launch {
                // Эмитим новые настройки, если в SharedPreferences произошло изменение
                _settingsFlow.emit(sharedPreferences.getSettingsOrCreateIfNull())
            }
        }
    }

    /**
     * Сохраняет настройки, применяя функцию преобразования к текущим настройкам.
     * Обновленные настройки сохраняются в [SharedPreferences] и автоматически передаются
     * подписчикам через [StateFlow].
     *
     * @param saving Функция, которая принимает текущие настройки и возвращает обновленные.
     */
    @Synchronized
    override fun save(saving: (Settings) -> Settings) {
        // Обновляем настройки в памяти
        settings = saving(settings)
        // Сохраняем обновленные настройки в SharedPreferences
        sharedPreferences.save(settings)
    }
}