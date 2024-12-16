package ru.vafeen.universityschedule.domain.usecase.db

import ru.vafeen.universityschedule.domain.database.LessonRepository
import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

/**
 * UseCase для обновления пар в базе данных.
 *
 * Этот класс отвечает за обновление одного или нескольких уроков в репозитории.
 *
 * @property lessonRepository Репозиторий, используемый для взаимодействия с данными пар.
 */
class UpdateLessonsUseCase(private val lessonRepository: LessonRepository) : UseCase {

    /**
     * Обновляет указанные пары в базе данных.
     *
     * @param lessons Пары, которые нужно обновить. Можно передавать несколько пар в виде vararg.
     */
    suspend fun invoke(vararg lessons: Lesson) =
        lessonRepository.updateLessons(lessons.toList())
}
