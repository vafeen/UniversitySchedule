package ru.vafeen.universityschedule.domain.utils

import ru.vafeen.universityschedule.domain.models.Lesson

/**
 * Проверяет, содержится ли указанная пара в коллекции пар.
 *
 * Эта функция фильтрует коллекцию пар и проверяет, есть ли в ней пара,
 * которая совпадает со всеми параметрами указанной пары.
 *
 * @param lesson Пара, которую необходимо найти в коллекции.
 * @return Пара [Lesson], если она найдена в коллекции, или null, если пара не найдена.
 */
internal fun Iterable<Lesson>.containsLesson(lesson: Lesson): Lesson? = filter {
    it.dayOfWeek == lesson.dayOfWeek &&
            it.name == lesson.name &&
            it.startTime == lesson.startTime &&
            it.endTime == lesson.endTime &&
            it.classroom == lesson.classroom &&
            it.teacher == lesson.teacher &&
            it.subGroup == lesson.subGroup &&
            it.frequency == lesson.frequency
}.let {
    if (it.isEmpty()) null // Возвращаем null, если совпадений нет.
    else it[0] // Возвращаем первую найденную пару.
}
