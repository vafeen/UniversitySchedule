package ru.vafeen.universityschedule.domain.network.result

/**
 * Запечатанный класс для обработки результатов сетевых запросов.
 *
 * @param T Тип данных, который возвращается в случае успешного выполнения запроса.
 */
sealed class ResponseResult<T> {

    /**
     * Класс, представляющий успешный результат запроса.
     *
     * @property data Данные, полученные в результате успешного выполнения запроса.
     */
    class Success<T>(val data: T) : ResponseResult<T>()

    /**
     * Класс, представляющий ошибку при выполнении запроса.
     *
     * @property exception Исключение, произошедшее во время выполнения запроса.
     */
    class Error<T>(val exception: Exception) : ResponseResult<T>()
}
