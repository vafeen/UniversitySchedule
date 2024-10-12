package ru.vafeen.universityschedule.noui.di


import androidx.room.Room
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.universityschedule.database.AppDatabase
import ru.vafeen.universityschedule.database.DBInfo
import ru.vafeen.universityschedule.database.DatabaseRepository


val koinDatabaseDIModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            context = get(), klass = AppDatabase::class.java, name = DBInfo.NAME
        ).addMigrations(
            AppDatabase.MIGRATION_1_2,
            AppDatabase.MIGRATION_2_3,
            AppDatabase.MIGRATION_3_4,
            AppDatabase.MIGRATION_4_5,
        ).build()
    }
    singleOf(::DatabaseRepository)
}