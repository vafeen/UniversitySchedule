package ru.vafeen.universityschedule.data.impl.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import kotlinx.coroutines.flow.first
import org.koin.java.KoinJavaComponent.getKoin
import ru.vafeen.universityschedule.domain.MainActivityIntentProvider
import ru.vafeen.universityschedule.domain.scheduler.Scheduler
import ru.vafeen.universityschedule.domain.scheduler.SchedulerAPIMigrationManager
import ru.vafeen.universityschedule.domain.usecase.db.GetAsFlowRemindersUseCase

/**
 * Реализация менеджера миграции API для планировщика.
 *
 * Этот класс отвечает за миграцию напоминаний, отменяя старые будильники и планируя новые.
 *
 * @property context Контекст приложения, используемый для доступа к системным службам.
 * @property alarmManager Менеджер будильников для управления запланированными событиями.
 * @property getAsFlowRemindersUseCase Используется для получения всех напоминаний в виде потока.
 * @property mainActivityIntentProvider Провайдер интента для запуска главной активности.
 * @property scheduler Сервис для планирования задач.
 */
internal class SchedulerAPIMigrationManagerImpl(private val context: Context) :
    SchedulerAPIMigrationManager {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val getAsFlowRemindersUseCase = getKoin().get<GetAsFlowRemindersUseCase>()
    private val mainActivityIntentProvider = getKoin().get<MainActivityIntentProvider>()
    private val scheduler = getKoin().get<Scheduler>()

    /**
     * Выполняет миграцию напоминаний.
     *
     * Отменяет старые будильники и планирует новые на основе существующих напоминаний.
     */
    override suspend fun migrate() {
        val allReminders = getAsFlowRemindersUseCase.invoke().first() // Получаем все напоминания

        allReminders.forEach { reminder ->
            // Отменяем старый будильник для каждого напоминания
            alarmManager.cancel(
                PendingIntent.getBroadcast(
                    context,
                    reminder.idOfReminder,
                    mainActivityIntentProvider.provideIntent(),
                    PendingIntent.FLAG_IMMUTABLE
                )
            )
            // Планируем новое повторяющееся задание для напоминания
            scheduler.scheduleRepeatingJob(reminder)
        }
    }
}