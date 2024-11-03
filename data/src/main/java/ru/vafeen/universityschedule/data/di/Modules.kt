package ru.vafeen.universityschedule.data.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.universityschedule.data.converters.DateTimeConverter
import ru.vafeen.universityschedule.data.converters.LessonConverter
import ru.vafeen.universityschedule.data.converters.ReleaseConverter
import ru.vafeen.universityschedule.data.converters.ReminderConverter
import ru.vafeen.universityschedule.data.converters.TimeConverter

val converterModule = module {
    singleOf(::DateTimeConverter)
    singleOf(::LessonConverter)
    singleOf(::ReleaseConverter)
    singleOf(::ReminderConverter)
    singleOf(::TimeConverter)
}



