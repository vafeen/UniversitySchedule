package ru.vafeen.universityschedule.data.impl.scheduler

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.runBlocking
import org.koin.java.KoinJavaComponent.getKoin
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.domain.MainActivityIntentProvider
import ru.vafeen.universityschedule.domain.models.model_additions.ReminderType
import ru.vafeen.universityschedule.domain.notifications.NotificationBuilder
import ru.vafeen.universityschedule.domain.notifications.NotificationService
import ru.vafeen.universityschedule.domain.scheduler.SchedulerExtra
import ru.vafeen.universityschedule.domain.usecase.db.GetReminderByIdOfReminderUseCase

/**
 * ReminderWorker — класс для обработки напоминаний в фоновом режиме с использованием WorkManager.
 *
 * Этот класс запускает напоминания с использованием переданных данных, таких как ID напоминания,
 * и вызывает соответствующие уведомления через сервисы уведомлений.
 *
 * @param context Контекст приложения, необходимый для работы WorkManager.
 * @param params Параметры, переданные Worker-у, включая данные для обработки.
 *
 * @property notificationService [NotificationService] отвечает за отображение уведомлений пользователю.
 * @property notificationBuilder [NotificationBuilder] используется для создания кастомных уведомлений.
 * @property getReminderByIdOfReminderUseCase [GetReminderByIdOfReminderUseCase] для получения напоминания по ID из базы данных.
 * @property mainActivityIntentProvider [MainActivityIntentProvider] создает Intent для запуска главной активности.
 * @property data Данные, переданные Worker-у через [WorkerParameters], включая ID напоминания.
 */
class ReminderWorker(context: Context, params: WorkerParameters) :
    Worker(context, params) {

    // Зависимости, инжектируемые через Koin
    private val notificationService: NotificationService by inject(
        clazz = NotificationService::class.java
    )
    private val notificationBuilder: NotificationBuilder = getKoin().get()
    private val getReminderByIdOfReminderUseCase: GetReminderByIdOfReminderUseCase = getKoin().get()
    private val mainActivityIntentProvider = getKoin().get<MainActivityIntentProvider>()

    // Данные, переданные через WorkerParameters
    private val data = params.inputData

    /**
     * Выполняет основную работу по обработке напоминаний.
     *
     * - Получает ID напоминания из данных.
     * - Извлекает напоминание из базы данных с помощью [GetReminderByIdOfReminderUseCase].
     * - В зависимости от типа напоминания ([ReminderType]) отправляет соответствующее уведомление.
     *
     * @return Результат выполнения работы:
     * - [Result.success()] — если задача выполнена успешно.
     * - [Result.failure()] — в случае ошибки или исключения.
     */
    override fun doWork(): Result = runBlocking {
        try {
            // Получение ID напоминания из входных данных
            val idOfReminder = data.getInt(
                SchedulerExtra.ID_OF_REMINDER,
                -1 // Значение по умолчанию, если ID не найден
            )

            // Извлечение напоминания из базы данных
            val reminder = getReminderByIdOfReminderUseCase.invoke(
                idOfReminder = idOfReminder
            )

            // Определяем тип напоминания и отправляем уведомление
            when (reminder?.type) {
                ReminderType.BEFORE_LESSON -> {
                    notificationService.showNotification(
                        notificationBuilder.createNotificationAbout15MinutesBeforeLesson(
                            title = reminder.title,
                            text = reminder.text,
                            mainActivityIntentProvider.provideIntent()
                        )
                    )
                }

                ReminderType.AFTER_BEGINNING_LESSON -> {
                    notificationService.showNotification(
                        notificationBuilder.createNotificationAfterBeginningLessonForBeCheckedAtThisLesson(
                            title = reminder.title,
                            text = reminder.text,
                            mainActivityIntentProvider.provideIntent()
                        )
                    )
                }

                // Если напоминание не найдено
                null -> {
                    notificationService.showNotification(
                        notificationBuilder.createNotificationAfterBeginningLessonForBeCheckedAtThisLesson(
                            title = "null",
                            text = "Напоминание не найдено",
                            mainActivityIntentProvider.provideIntent()
                        )
                    )
                }
            }

            Result.success()
        } catch (ex: Exception) {
            Result.failure()
        }
    }
}
