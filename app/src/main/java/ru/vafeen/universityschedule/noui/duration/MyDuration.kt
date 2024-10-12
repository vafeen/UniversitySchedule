package ru.vafeen.universityschedule.noui.duration

class MyDuration(val milliSeconds: Long) {
    companion object {
        fun ofTime(
            seconds: Long = 0,
            minutes: Long = 0,
            hours: Long = 0,
            days: Long = 0,
        ): MyDuration =
            MyDuration(milliSeconds = (seconds + minutes * 60 + hours * 60 * 60 + days * 86400) * 1000)

    }

    operator fun plus(myDuration: MyDuration): MyDuration =
        MyDuration(milliSeconds = milliSeconds + myDuration.milliSeconds)

    operator fun minus(myDuration: MyDuration): MyDuration =
        MyDuration(milliSeconds = if (milliSeconds > myDuration.milliSeconds) milliSeconds - myDuration.milliSeconds else 0)
}