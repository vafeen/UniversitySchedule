package ru.vafeen.universityschedule.noui.lesson_additions


enum class Frequency(var ruName: String) {
    Every(
        ruName = "Всегда"
    ) {
        override fun getOpposite(): Frequency = Every
    },
    Numerator(
        ruName = "Числитель"
    ) {
        override fun getOpposite(): Frequency = Denominator
    },
    Denominator(
        ruName = "Знаменатель"
    ) {
        override fun getOpposite(): Frequency = Numerator
    };

    abstract fun getOpposite(): Frequency
}