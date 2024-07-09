package ru.vafeen.universityschedule.noui.lesson_additions

import ru.vafeen.universityschedule.R


enum class Frequency(val resourceName: Int) {
    Every(resourceName = R.string.every) {
        override fun getOpposite(): Frequency = Every
    },
    Numerator(resourceName = R.string.numerator) {
        override fun getOpposite(): Frequency = Denominator
    },
    Denominator(resourceName = R.string.denominator) {
        override fun getOpposite(): Frequency = Numerator
    };

    abstract fun getOpposite(): Frequency
}