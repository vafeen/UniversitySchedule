package ru.vafeen.universityschedule.domain.scheduler.duration

import ru.vafeen.universityschedule.resources.R

/**
 * Перечисление, представляющее различные частоты повторения.
 *
 * Каждое значение включает продолжительность и ресурсное имя для отображения.
 *
 * @property duration Продолжительность в виде объекта [MyDuration].
 * @property resourceName Ресурсная строка для отображения названия частоты.
 */
enum class RepeatDuration(val duration: MyDuration, val resourceName: Int) {
    /**
     * Повторение каждую неделю.
     */
    EVERY_WEEK(duration = MyDuration.ofTime(days = 7), resourceName = R.string.every_week),

    /**
     * Повторение каждые две недели.
     */
    EVERY_2_WEEKS(duration = MyDuration.ofTime(days = 14), resourceName = R.string.every_2_weeks);
}
