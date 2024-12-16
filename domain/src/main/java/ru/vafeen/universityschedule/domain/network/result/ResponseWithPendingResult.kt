package ru.vafeen.universityschedule.domain.network.result

/**
 * Запечатанный класс для обработки результатов сетевых запросов с учетом состояния ожидания.
 *
 * @param T Тип данных, который возвращается в случае успешного выполнения запроса.
 */
sealed class ResponseWithPendingResult<T> {

    /**
     * Класс, представляющий ошибку при выполнении запроса.
     *
     * @property exception Исключение, произошедшее во время выполнения запроса.
     */
    class Error<T>(val exception: Exception) : ResponseWithPendingResult<T>()

    /**
     * Класс, представляющий состояние ожидания результата запроса.
     */
    class Pending<T> : ResponseWithPendingResult<T>()

    /**
     * Класс, представляющий успешный результат запроса.
     *
     * @property data Данные, полученные в результате успешного выполнения запроса.
     */
    class Success<T>(val data: T) : ResponseWithPendingResult<T>()
}
