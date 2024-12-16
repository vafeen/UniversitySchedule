package ru.vafeen.universityschedule.domain.usecase.db

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.domain.usecase.base.UseCase
import ru.vafeen.universityschedule.domain.utils.containsLesson

/**
 * UseCase для интеллектуального обновления пар.
 *
 * Этот класс отвечает за:
 * 1. Добавление новых пар в базу данных.
 * 2. Удаление старых пар, которые отсутствуют в новых данных.
 * 3. Удаление напоминаний, связанных с удаленными парами.
 *
 * @property getAsFlowLessonsUseCase UseCase для получения текущего списка пар в виде Flow.
 * @property insertLessonsUseCase UseCase для добавления новых пар в базу данных.
 * @property deleteLessonsUseCase UseCase для удаления пар из базы данных.
 * @property deleteUseLessRemindersForLessonsUseCase UseCase для удаления напоминаний, связанных с удаленными парами.
 */
class CleverUpdatingLessonsUseCase(
    private val getAsFlowLessonsUseCase: GetAsFlowLessonsUseCase,
    private val insertLessonsUseCase: InsertLessonsUseCase,
    private val deleteLessonsUseCase: DeleteLessonsUseCase,
    private val deleteUseLessRemindersForLessonsUseCase: DeleteUseLessRemindersForLessonsUseCase,
) : UseCase {

    /**
     * Выполняет обновление пар.
     *
     * Сравнивает текущий список пар с новыми данными и выполняет следующие действия:
     * 1. Добавляет новые пары, которых еще нет в базе данных.
     * 2. Удаляет пары, отсутствующие в новых данных.
     * 3. Удаляет напоминания, связанные с удаленными парами.
     *
     * @param newLessons Список новых пар, которые должны быть в базе данных.
     */
    fun invoke(newLessons: List<Lesson>) {
        // Запускаем обновление в отдельной корутине, используя IO-диспетчер.
        CoroutineScope(Dispatchers.IO).launch {
            // Получаем текущий список пар из базы данных.
            val lastLessons = getAsFlowLessonsUseCase.invoke().first()

            // Список для добавления новых или обновленных пар.
            val result = mutableListOf<Lesson>()
            // Список для удаления старых пар, которых нет в новых данных.
            val resultForDelete = mutableListOf<Lesson>()

            // Проходимся по новым парам.
            for (newLesson in newLessons) {
                // Если текущая пара уже есть, оставляем существующую запись.
                // Если нет, добавляем новую.
                result.add(lastLessons.containsLesson(lesson = newLesson) ?: newLesson)
            }

            // Проходимся по текущим парам в базе данных.
            for (lastLesson in lastLessons) {
                // Если пара отсутствует в новых данных, добавляем ее в список для удаления.
                newLessons.containsLesson(lesson = lastLesson).let {
                    if (it == null)
                        resultForDelete.add(lastLesson)
                }
            }

            // Добавляем новые или обновленные пары в базу данных.
            insertLessonsUseCase.invoke(*result.toTypedArray())
            // Удаляем старые пары из базы данных.
            deleteLessonsUseCase.invoke(*resultForDelete.toTypedArray())
            // Удаляем напоминания, связанные с удаленными парами.
            deleteUseLessRemindersForLessonsUseCase.invoke(resultForDelete)
        }
    }
}
