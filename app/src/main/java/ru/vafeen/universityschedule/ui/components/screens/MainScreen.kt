package ru.vafeen.universityschedule.ui.components.screens

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vafeen.universityschedule.database.entity.Lesson
import ru.vafeen.universityschedule.noui.lesson_additions.Frequency
import ru.vafeen.universityschedule.ui.components.TextForThisTheme
import ru.vafeen.universityschedule.ui.components.bottom_bar.BottomBar
import ru.vafeen.universityschedule.ui.components.ui_utils.CardOfNextLesson
import ru.vafeen.universityschedule.ui.components.ui_utils.StringForSchedule
import ru.vafeen.universityschedule.ui.components.viewModels.MainScreenViewModel
import ru.vafeen.universityschedule.ui.navigation.Screen
import ru.vafeen.universityschedule.ui.theme.FontSize
import ru.vafeen.universityschedule.ui.theme.ScheduleTheme
import ru.vafeen.universityschedule.utils.SharedPreferencesValue
import ru.vafeen.universityschedule.utils.createGSheetsService
import ru.vafeen.universityschedule.utils.getDateString
import ru.vafeen.universityschedule.utils.getTimeStringAsHMS
import ru.vafeen.universityschedule.utils.nowIsLesson
import java.time.LocalDate
import java.time.LocalTime

// для получения данных достаточно просто открыть доступ и вставить ссылку

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController, viewModel: MainScreenViewModel, context: Context
) {
    val link = context.getSharedPreferences(
        SharedPreferencesValue.Name.key, Context.MODE_PRIVATE
    ).getString(SharedPreferencesValue.Link.key, "") ?: ""
    viewModel.gSheetsService = createGSheetsService(link = link)

    var lessons by remember {
        mutableStateOf(listOf<Lesson>())
    }
    LaunchedEffect(key1 = null) {
        viewModel.updateLocalDatabase {
            lessons = it
        }
    }
    val cor = rememberCoroutineScope()
    var localTime by remember {
        mutableStateOf(LocalTime.now())
    }
    var localDate by remember {
        mutableStateOf(LocalDate.now())
    }

    val pagerState = rememberPagerState(
        pageCount = {
            7
        }, initialPage = localDate.dayOfWeek.value - 1
    )

    LaunchedEffect(key1 = null) {
        withContext(Dispatchers.Main) {
            while (true) {
                localTime = LocalTime.now()
                delay(1000L)
            }
        }
    }
    Scaffold(containerColor = ScheduleTheme.colors.singleTheme, topBar = {
        TopAppBar(colors = TopAppBarColors(
            containerColor = ScheduleTheme.colors.singleTheme,
            scrolledContainerColor = ScheduleTheme.colors.singleTheme,
            navigationIconContentColor = ScheduleTheme.colors.oppositeTheme,
            titleContentColor = ScheduleTheme.colors.oppositeTheme,
            actionIconContentColor = ScheduleTheme.colors.singleTheme
        ), modifier = Modifier.fillMaxWidth(), title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = if (viewModel.weekOfYear == Frequency.Numerator) "Числитель"
                    else "Знаменатель",
                    fontSize = FontSize.huge,
                    color = ScheduleTheme.colors.oppositeTheme
                )

                TextForThisTheme(
                    text = "|",
                    fontSize = FontSize.huge,
                )

                TextForThisTheme(
                    text = localDate.getDateString(),
                    fontSize = FontSize.huge,
                )

                TextForThisTheme(
                    text = "|",
                    fontSize = FontSize.huge,
                )

                TextForThisTheme(
                    text = localTime.getTimeStringAsHMS(),
                    fontSize = FontSize.huge,
                )

            }
        })
    }, bottomBar = {
        BottomBar(
            clickToScreen2 = { navController.navigate(Screen.Settings.route) }, selected1 = true
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(3.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {

                    for (index in 0..viewModel.daysOfWeek.lastIndex) {
                        val day = viewModel.daysOfWeek[index]
                        Card(modifier = Modifier
                            .padding(horizontal = 3.dp)
                            .clickable {
                                cor.launch(Dispatchers.Main) {
                                    pagerState.scrollToPage(index)
                                    localDate =
                                        viewModel.todayDate
                                            .plusDays((day.value - viewModel.todayDate.dayOfWeek.value).toLong())
                                }
                            }
                            .alpha(
                                if (day != viewModel.todayDate.dayOfWeek &&
                                    day != viewModel.daysOfWeek[pagerState.currentPage]
                                )
                                    0.5f else 1f
                            ), colors = CardDefaults.cardColors(
                            containerColor =
                            if (day == viewModel.todayDate.dayOfWeek) ScheduleTheme.colors.oppositeTheme
                            else ScheduleTheme.colors.buttonColor,
                            contentColor =
                            if (day == viewModel.todayDate.dayOfWeek) ScheduleTheme.colors.singleTheme
                            else ScheduleTheme.colors.oppositeTheme
                        )) {
                            Text(
                                text = viewModel.ruDaysOfWeek[index],
                                fontSize = FontSize.medium,
                                modifier = Modifier.padding(
                                    vertical = 5.dp, horizontal = 10.dp
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            HorizontalPager(state = pagerState) { page ->
                localDate =
                    viewModel.todayDate.plusDays((pagerState.currentPage + 1 - viewModel.todayDate.dayOfWeek.value).toLong())

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    val lessonsOfThisDay = lessons.filter {
                        it.dayOfWeek == viewModel.daysOfWeek[page] && (it.frequency == viewModel.weekOfYear || it.frequency == Frequency.Every)
                    }
                    val lessonsInOppositeNumAndDenDay = lessons.filter {
                        it.dayOfWeek == viewModel.daysOfWeek[page] && it.frequency != Frequency.Every && it.frequency == viewModel.weekOfYear.getOpposite()
                    }

                    if (lessonsOfThisDay.isNotEmpty() || lessonsInOppositeNumAndDenDay.isNotEmpty()) {
                        if (lessonsOfThisDay.isNotEmpty()) {
                            viewModel.nowIsLesson = false
                            for (indexOfLesson in 0..lessonsOfThisDay.lastIndex) {
                                val thisLesson = lessonsOfThisDay[indexOfLesson]
                                if (thisLesson.nowIsLesson(localTime) && viewModel.daysOfWeek[page] == localDate.dayOfWeek) {
                                    viewModel.nowIsLesson = true
                                    thisLesson.StringForSchedule(colorBack = ScheduleTheme.colors.mainColor)
                                } else if (viewModel.daysOfWeek[page] == localDate.dayOfWeek && lessonsOfThisDay.any {
                                        it.startTime > localTime
                                    } && thisLesson == lessonsOfThisDay.filter {
                                        it.startTime > localTime
                                    }[0] && !viewModel.nowIsLesson) {
                                    CardOfNextLesson {
                                        thisLesson.StringForSchedule(
                                            colorBack = ScheduleTheme.colors.buttonColor,
                                            padding = 0.dp
                                        )
                                    }
                                } else thisLesson.StringForSchedule(colorBack = ScheduleTheme.colors.buttonColor)

                            }
                        }

                        Spacer(modifier = Modifier.height(15.dp))

                        if (lessonsInOppositeNumAndDenDay.isNotEmpty()) {
                            for (indexOfLesson in 0..lessonsInOppositeNumAndDenDay.lastIndex) {
                                lessonsInOppositeNumAndDenDay[indexOfLesson].StringForSchedule(
                                    colorBack = ScheduleTheme.colors.buttonColor,
                                    lessonOfThisNumAndDenOrNot = false
                                )
                            }
                        }


                    } else {
//                        Cats()
                    }
                }

            }


        }
    }


}

