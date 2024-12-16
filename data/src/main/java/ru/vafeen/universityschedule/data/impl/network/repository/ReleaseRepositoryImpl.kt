package ru.vafeen.universityschedule.data.impl.network.repository

import ru.vafeen.universityschedule.data.converters.ReleaseConverter
import ru.vafeen.universityschedule.data.network.service.GitHubDataService
import ru.vafeen.universityschedule.domain.models.Release
import ru.vafeen.universityschedule.domain.network.result.ResponseResult
import ru.vafeen.universityschedule.domain.network.service.ReleaseRepository
import java.io.IOException
import java.net.UnknownHostException

/**
 * Реализация репозитория для получения информации о релизах из GitHub.
 *
 * @property gitHubDataService Сервис для выполнения запросов к API GitHub.
 * @property releaseConverter Конвертер для преобразования объектов Release между слоями.
 */
internal class ReleaseRepositoryImpl(
    private val gitHubDataService: GitHubDataService,
    private val releaseConverter: ReleaseConverter
) : ReleaseRepository {

    /**
     * Получение последнего релиза из GitHub.
     *
     * @return Результат запроса, содержащий информацию о релизе или ошибку.
     */
    override suspend fun getLatestRelease(): ResponseResult<Release> {
        return try {
            // Выполнение запроса
            val response = gitHubDataService.getLatestRelease()
            val body = response.body()

            // Проверка на успешный ответ (например, статус 200)
            if (response.isSuccessful && body != null) {
                // Возвращаем успешный результат
                ResponseResult.Success(releaseConverter.convertAB(body))
            } else {
                // Обработка HTTP ошибки, если статус не успешен
                ResponseResult.Error(Exception("Ошибка сервера: ${response.code()}"))
            }
        } catch (e: UnknownHostException) {
            // Ошибка при отсутствии интернета
            ResponseResult.Error(UnknownHostException("Нет подключения к интернету"))
        } catch (e: IOException) {
            // Общая ошибка сети
            ResponseResult.Error(IOException("Ошибка сети: ${e.localizedMessage}"))
        } catch (e: Exception) {
            // Обработка любых других ошибок
            ResponseResult.Error(Exception("Неизвестная ошибка: ${e.localizedMessage}"))
        }
    }
}