package ru.vafeen.universityschedule.noui.dependency_injection

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.vafeen.universityschedule.database.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseDIModule {
    private val name = "universityScheduleDB.db"

    @Provides
    @Singleton
    fun injectDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context, klass = AppDatabase::class.java, name = name
        ).build()
    }
}
