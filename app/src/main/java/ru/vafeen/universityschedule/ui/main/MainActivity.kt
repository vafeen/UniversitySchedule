package ru.vafeen.universityschedule.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.vafeen.universityschedule.database.AppDatabase
import ru.vafeen.universityschedule.database.entity.Lesson
import ru.vafeen.universityschedule.noui.lesson_additions.Frequency
import ru.vafeen.universityschedule.ui.navigation.AppNavHost
import ru.vafeen.universityschedule.ui.theme.MainTheme
import java.time.DayOfWeek
import java.time.LocalTime
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var database: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            database.lessonDao().let {
                LaunchedEffect(key1 = null) {
                    withContext(Dispatchers.IO) {
                        it.insertAll(
                            Lesson(
                                dayOfWeek = DayOfWeek.MONDAY,
                                name = "name",
                                startTime = LocalTime.now(),
                                endTime = LocalTime.now(),
                                classroom = "301",
                                teacher = "teacher",
                                subGroup = 1,
                                frequency = Frequency.Every
                            )
                        )
                    }

                    it.getAllAsFlow().collect {
                        Log.d("list", "list = $it")
                    }


                }
            }


//            MainTheme {
//                AppNavHost()
//            }
        }
    }
}

