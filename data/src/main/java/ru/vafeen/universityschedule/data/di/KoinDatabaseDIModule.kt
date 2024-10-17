package ru.vafeen.universityschedule.data.di


import androidx.room.Room
import org.koin.dsl.module
import ru.vafeen.universityschedule.data.database.AppDatabase
import ru.vafeen.universityschedule.data.database.DBInfo


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
}