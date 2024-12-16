package ru.vafeen.universityschedule.data.impl.scheduler

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.getKoin
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.domain.MainActivityIntentProvider
import ru.vafeen.universityschedule.domain.models.model_additions.ReminderType
import ru.vafeen.universityschedule.domain.notifications.NotificationBuilder
import ru.vafeen.universityschedule.domain.notifications.NotificationService
import ru.vafeen.universityschedule.domain.scheduler.SchedulerExtra
import ru.vafeen.universityschedule.domain.usecase.db.GetReminderByIdOfReminderUseCase

/**
 * Рабочий класс для обработки напоминаний в фоновом режиме.
 *
 * @property notificationService Сервис для отображения уведомлений.
 * @property notificationBuilder Строитель уведомлений для создания уведомлений.
 * @property getReminderByIdOfReminderUseCase Используется для получения напоминания по его идентификатору.
 * @property mainActivityIntentProvider Провайдер интента для запуска главной активности.
 * @property data Данные, переданные в рабочий класс.
 */
class ReminderWorker(context: Context, params: WorkerParameters) :
    Worker(context, params) {

    private val notificationService: NotificationService by inject(
        clazz = NotificationService::class.java
    )
    private val notificationBuilder: NotificationBuilder = getKoin().get()
    private val getReminderByIdOfReminderUseCase: GetReminderByIdOfReminderUseCase = getKoin().get()
    private val mainActivityIntentProvider = getKoin().get<MainActivityIntentProvider>()
    private val data = params.inputData

    /**
     * Выполняет работу по обработке напоминаний.
     *
     * @return Результат выполнения работы. Возвращает [Result.success()] при успешном завершении,
     *         или [Result.failure()] в случае ошибки.
     */
    override fun doWork(): Result = try {
        val idOfReminder = data.getInt(
            SchedulerExtra.ID_OF_REMINDER,
            -1 // Значение по умолчанию, если ID не найден
        )
        CoroutineScope(Dispatchers.IO).launch {
            val reminder = getReminderByIdOfReminderUseCase.invoke(
                idOfReminder = idOfReminder
            )
            reminder?.let {
                when (reminder.type) {
                    ReminderType.BEFORE_LESSON -> {
                        notificationService.showNotification(
                            notificationBuilder.createNotificationAbout15MinutesBeforeLesson(
                                title = it.title,
                                text = it.text,
                                mainActivityIntentProvider.provideIntent()
                            )
                        )
                    }

                    ReminderType.AFTER_BEGINNING_LESSON -> {
                        notificationService.showNotification(
                            notificationBuilder.createNotificationAfterBeginningLessonForBeCheckedAtThisLesson(
                                title = it.title,
                                text = it.text,
                                mainActivityIntentProvider.provideIntent()
                            )
                        )
                    }
                }
            }
        }
        Result.success() // Возвращаем успех после запуска корутины
    } catch (ex: Exception) {
        Result.failure() // Возвращаем ошибку в случае исключения
    }
}
