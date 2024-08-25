package ru.vafeen.universityschedule.ui.components.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vafeen.universityschedule.R
import ru.vafeen.universityschedule.database.entity.Lesson
import ru.vafeen.universityschedule.network.downloader.Downloader
import ru.vafeen.universityschedule.network.downloader.Progress
import ru.vafeen.universityschedule.ui.components.bottom_bar.BottomBar
import ru.vafeen.universityschedule.ui.components.ui_utils.CardOfNextLesson
import ru.vafeen.universityschedule.ui.components.ui_utils.StringForSchedule
import ru.vafeen.universityschedule.ui.components.ui_utils.TextForThisTheme
import ru.vafeen.universityschedule.ui.components.ui_utils.WeekDay
import ru.vafeen.universityschedule.ui.components.viewModels.MainScreenViewModel
import ru.vafeen.universityschedule.ui.navigation.Screen
import ru.vafeen.universityschedule.ui.theme.FontSize
import ru.vafeen.universityschedule.ui.theme.ScheduleTheme
import ru.vafeen.universityschedule.utils.GSheetsServiceRequestStatus
import ru.vafeen.universityschedule.utils.Path
import ru.vafeen.universityschedule.utils.getDateString
import ru.vafeen.universityschedule.utils.getIconByRequestStatus
import ru.vafeen.universityschedule.utils.getMainColorForThisTheme
import ru.vafeen.universityschedule.utils.getSettingsOrCreateIfNull
import ru.vafeen.universityschedule.utils.getTimeStringAsHMS
import ru.vafeen.universityschedule.utils.nowIsLesson
import ru.vafeen.universityschedule.utils.suitableColor
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController, viewModel: MainScreenViewModel
) {
    val context = LocalContext.current
    val defaultColor = ScheduleTheme.colors.mainColor
    val progress = remember {
        mutableStateOf(Progress(totalBytesRead = 0L, contentLength = 0L, done = false))
    }
    val dark = isSystemInDarkTheme()
    val settings by remember { mutableStateOf(viewModel.sharedPreferences.getSettingsOrCreateIfNull()) }
    val mainColor by remember {
        mutableStateOf(
            settings.getMainColorForThisTheme(isDark = dark) ?: defaultColor
        )
    }
    var isUpdateInProcess by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = null) {
        Downloader.isUpdateInProcessFlow.collect {
            isUpdateInProcess = it
        }
    }
    LaunchedEffect(key1 = null) {
        Downloader.sizeFlow.collect {
            progress.value = it
            if (it.contentLength == it.totalBytesRead) {
                isUpdateInProcess = false
                Downloader.installApk(
                    context = context,
                    apkFilePath = Path.path(context)
                )
            }
        }
    }
    var networkState by remember {
        mutableStateOf(GSheetsServiceRequestStatus.Waiting)
    }
    var lessons by remember {
        mutableStateOf(listOf<Lesson>())
    }

    LaunchedEffect(key1 = null) {
        viewModel.updateLocalDatabase { newLessons, problem ->
            lessons = newLessons
            networkState = problem
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
                delay(timeMillis = 1000L)
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
                Icon(
                    painter = painterResource(
                        id = getIconByRequestStatus(networkState = networkState)
                    ),
                    contentDescription = "data updating state",
                    tint = ScheduleTheme.colors.oppositeTheme
                )

                TextForThisTheme(
                    text = "|",
                    fontSize = FontSize.big22,
                )

                Text(
                    text = stringResource(id = viewModel.weekOfYear.resourceName),
                    fontSize = FontSize.big22,
                    color = ScheduleTheme.colors.oppositeTheme
                )

                TextForThisTheme(
                    text = "|",
                    fontSize = FontSize.big22,
                )

                TextForThisTheme(
                    text = "${localDate.getDateString()} ${localTime.getTimeStringAsHMS()}",
                    fontSize = FontSize.big22,
                )
            }
        })
    }, bottomBar = {
        BottomBar(
            containerColor = mainColor, clickToScreen2 = {
                navController.navigate(Screen.Settings.route)
            }, selected1 = true
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
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
                                pagerState.animateScrollToPage(index)
                                localDate =
                                    viewModel.todayDate.plusDays((day.value - viewModel.todayDate.dayOfWeek.value).toLong())
                            }
                        }
                        .alpha(
                            if (day != viewModel.todayDate.dayOfWeek && day != viewModel.daysOfWeek[pagerState.currentPage]) 0.5f else 1f
                        ), colors = CardDefaults.cardColors(
                        containerColor = if (day == viewModel.todayDate.dayOfWeek) mainColor
                        else ScheduleTheme.colors.buttonColor,
                        contentColor = (if (day == viewModel.todayDate.dayOfWeek) mainColor
                        else ScheduleTheme.colors.buttonColor).suitableColor()
                    )) {
                        Text(
                            text = viewModel.ruDaysOfWeek[index],
                            fontSize = FontSize.small17,
                            modifier = Modifier.padding(
                                vertical = 5.dp, horizontal = 10.dp
                            )
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                HorizontalPager(
                    state = pagerState, modifier = Modifier.weight(10f)
                ) { page ->
                    localDate =
                        viewModel.todayDate.plusDays((pagerState.currentPage + 1 - viewModel.todayDate.dayOfWeek.value).toLong())
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        val lessonsOfThisDay = lessons
                            .filter {
                                it.dayOfWeek == viewModel.daysOfWeek[page] && (it.frequency == null || it.frequency == viewModel.weekOfYear) && (it.subGroup == settings.subgroup || settings.subgroup == null || it.subGroup == null)
                            }
                        val lessonsInOppositeNumAndDenDay = lessons.filter {
                            it.dayOfWeek == viewModel.daysOfWeek[page] && it.frequency == viewModel.weekOfYear.getOpposite() && (it.subGroup == settings.subgroup || settings.subgroup == null || it.subGroup == null)
                        }
                        if (lessonsOfThisDay.isNotEmpty()) {
                            viewModel.nowIsLesson = false
                            lessonsOfThisDay.forEach { lesson ->
                                if (lesson.nowIsLesson(localTime) && viewModel.daysOfWeek[page] == viewModel.todayDate.dayOfWeek) {
                                    viewModel.nowIsLesson = true
                                    lesson.StringForSchedule(colorBack = mainColor)
                                } else if (viewModel.daysOfWeek[page] == viewModel.todayDate.dayOfWeek && lessonsOfThisDay.any {
                                        it.startTime > localTime
                                    } && lesson == lessonsOfThisDay.filter {
                                        it.startTime > localTime
                                    }[0] && !viewModel.nowIsLesson) {
                                    CardOfNextLesson(colorOfCard = mainColor) {
                                        lesson.StringForSchedule(
                                            colorBack = ScheduleTheme.colors.buttonColor,
                                            padding = 0.dp
                                        )
                                    }
                                } else lesson.StringForSchedule(colorBack = ScheduleTheme.colors.buttonColor)
                            }
                        } else WeekDay(context = context, modifier = Modifier
                            .let {
                                var modifier = Modifier.padding(vertical = 75.dp)
                                if (lessonsInOppositeNumAndDenDay.isEmpty())
                                    modifier = Modifier.weight(1f)
                                modifier
                            })

                        if (lessonsInOppositeNumAndDenDay.isNotEmpty()) {
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(23.dp)
                                    .padding(vertical = 10.dp)
                                    .background(ScheduleTheme.colors.buttonColor)
                            )
                            TextForThisTheme(
                                text = stringResource(id = R.string.other_lessons_in_this_day),
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                fontSize = FontSize.big22
                            )
                            lessonsInOppositeNumAndDenDay.forEach { lesson ->
                                lesson.StringForSchedule(
                                    colorBack = ScheduleTheme.colors.buttonColor,
                                    lessonOfThisNumAndDenOrNot = false
                                )
                            }
                        }
                    }
                }
            }
            if (isUpdateInProcess) UpdateProgress(percentage = progress)
        }
    }
}

@Composable
fun UpdateProgress(percentage: MutableState<Progress>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .background(Color.LightGray),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextForThisTheme(
            text = "update in process ${
                percentage.value.let {
                    100 * it.totalBytesRead / (it.contentLength.let { cl ->
                        if (cl.toFloat() == 0f) 1 else cl
                    })
                }
            }"
        )
    }
}