package ru.vafeen.universityschedule.domain.models.model_additions

import ru.vafeen.universityschedule.resources.R


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