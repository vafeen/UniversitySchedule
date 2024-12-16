package ru.vafeen.universityschedule.domain.scheduler.duration

/**
 * Класс, представляющий продолжительность времени в миллисекундах.
 *
 * Этот класс используется для работы с длительностями времени без привязки к версии Android.
 *
 * @property milliSeconds Продолжительность в миллисекундах.
 */
class MyDuration(val milliSeconds: Long) {

    companion object {
        /**
         * Создает объект [MyDuration] из заданного количества секунд, минут, часов и дней.
         *
         * @param seconds Количество секунд (по умолчанию 0).
         * @param minutes Количество минут (по умолчанию 0).
         * @param hours Количество часов (по умолчанию 0).
         * @param days Количество дней (по умолчанию 0).
         * @return Новый объект [MyDuration], представляющий общую продолжительность.
         */
        fun ofTime(
            seconds: Long = 0,
            minutes: Long = 0,
            hours: Long = 0,
            days: Long = 0,
        ): MyDuration =
            MyDuration(milliSeconds = (seconds + minutes * 60 + hours * 60 * 60 + days * 86400) * 1000)
    }

    /**
     * Оператор сложения для объединения двух объектов [MyDuration].
     *
     * @param myDuration Объект [MyDuration], который нужно добавить.
     * @return Новый объект [MyDuration], представляющий сумму двух продолжительностей.
     */
    operator fun plus(myDuration: MyDuration): MyDuration =
        MyDuration(milliSeconds = milliSeconds + myDuration.milliSeconds)

    /**
     * Оператор вычитания для вычитания одной продолжительности из другой.
     *
     * @param myDuration Объект [MyDuration], который нужно вычесть.
     * @return Новый объект [MyDuration], представляющий разность двух продолжительностей.
     */
    operator fun minus(myDuration: MyDuration): MyDuration =
        MyDuration(milliSeconds = if (milliSeconds > myDuration.milliSeconds) milliSeconds - myDuration.milliSeconds else 0)
}
