package ru.vafeen.universityschedule.presentation.utils

import ru.vafeen.universityschedule.domain.models.Settings
import ru.vafeen.universityschedule.domain.models.model_additions.Frequency

/**
 * Функция для изменения частоты в зависимости от настроек.
 * Если в настройках указано, что выбранная частота соответствует номерам недель (isSelectedFrequencyCorrespondsToTheWeekNumbers),
 * то возвращается текущая частота. В противном случае возвращается противоположная частота.
 *
 * @param settings Настройки, содержащие информацию о выбранной частоте.
 * @return Возвращает текущую или противоположную частоту в зависимости от значения настроек.
 */
internal fun Frequency.changeFrequencyIfDefinedInSettings(settings: Settings): Frequency {
    return if (settings.isSelectedFrequencyCorrespondsToTheWeekNumbers != false)
        this
    else this.getOpposite()
}
