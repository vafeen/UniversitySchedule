package ru.vafeen.universityschedule.ui.components.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel
import ru.vafeen.universityschedule.R
import ru.vafeen.universityschedule.database.entity.Lesson
import ru.vafeen.universityschedule.network.downloader.Downloader
import ru.vafeen.universityschedule.network.downloader.Progress
import ru.vafeen.universityschedule.noui.lesson_additions.Frequency
import ru.vafeen.universityschedule.ui.components.bottom_bar.BottomBar
import ru.vafeen.universityschedule.ui.components.ui_utils.CardOfNextLesson
import ru.vafeen.universityschedule.ui.components.ui_utils.StringForSchedule
import ru.vafeen.universityschedule.ui.components.ui_utils.TextForThisTheme
import ru.vafeen.universityschedule.ui.components.ui_utils.UpdateProgress
import ru.vafeen.universityschedule.ui.components.ui_utils.WeekDay
import ru.vafeen.universityschedule.ui.components.viewModels.MainScreenViewModel
import ru.vafeen.universityschedule.ui.navigation.Screen
import ru.vafeen.universityschedule.ui.theme.FontSize
import ru.vafeen.universityschedule.ui.theme.ScheduleTheme
import ru.vafeen.universityschedule.utils.NotificationAboutLessonsSettings
import ru.vafeen.universityschedule.utils.Path
import ru.vafeen.universityschedule.utils.changeFrequencyIfDefinedInSettings
import ru.vafeen.universityschedule.utils.getDateString
import ru.vafeen.universityschedule.utils.getFrequencyByLocalDate
import ru.vafeen.universityschedule.utils.getMainColorForThisTheme
import ru.vafeen.universityschedule.utils.nowIsLesson
import ru.vafeen.universityschedule.utils.save
import ru.vafeen.universityschedule.utils.suitableColor
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
) {
    val viewModel: MainScreenViewModel = koinViewModel()
    val context = LocalContext.current
    val settings by viewModel.settings.collectAsState()
    val defaultColor = ScheduleTheme.colors.mainColor
    val progress = remember {
        mutableStateOf(Progress(totalBytesRead = 0L, contentLength = 0L, done = false))
    }
    val dark = isSystemInDarkTheme()
    val mainColor by remember {
        mutableStateOf(
            settings.getMainColorForThisTheme(isDark = dark) ?: defaultColor
        )
    }
    var isFrequencyInChanging by remember {
        mutableStateOf(false)
    }
    val isUpdateInProcess by Downloader.isUpdateInProcessFlow.collectAsState(false)
    val cor = rememberCoroutineScope()
    var localTime by remember {
        mutableStateOf(LocalTime.now())
    }
    var localDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var weekOfYear by remember {
        mutableStateOf(viewModel.weekOfYear)
    }

    LaunchedEffect(key1 = null) {
        Downloader.sizeFlow.collect {
            if (!it.failed) {
                progress.value = it
                if (it.contentLength == it.totalBytesRead) {
                    Downloader.isUpdateInProcessFlow.emit(false)
                    Downloader.installApk(
                        context = context, apkFilePath = Path.path(context)
                    )
                }
            } else Downloader.isUpdateInProcessFlow.emit(false)
        }
    }
    var lessons by remember {
        mutableStateOf(listOf<Lesson>())
    }
    val cardsWithDateState = rememberLazyListState()

    fun changeDateAndFrequency(daysAfterTodayDate: Long) {
        localDate = viewModel.todayDate.plusDays(daysAfterTodayDate)
        weekOfYear = localDate.getFrequencyByLocalDate()
            .changeFrequencyIfDefinedInSettings(settings = settings)
    }

    fun chooseTypeOfDefinitionFrequencyDependsOn(selectedFrequency: Frequency?) {
        viewModel.sharedPreferences.save(
            settings.copy(
                isSelectedFrequencyCorrespondsToTheWeekNumbers = selectedFrequency?.let {
                    localDate.getFrequencyByLocalDate() == it
                })
        )
        weekOfYear = localDate.getFrequencyByLocalDate()
            .changeFrequencyIfDefinedInSettings(settings = settings)
        isFrequencyInChanging = false
    }
    LaunchedEffect(key1 = null) {
        viewModel.databaseRepository.getAllAsFlowLessons().collect {
            lessons = it
        }
    }
//    LaunchedEffect(null) {
//        viewModel.databaseRepository.getAllRemindersAsFlow().collect {
//            Log.d("reminders", it.joinToString(separator = "\n"))
//        }
//    }

    val pagerState = rememberPagerState(
        pageCount = {
            viewModel.pageNumber
        }, initialPage = 0
    )
    BackHandler {
        when {
            pagerState.currentPage == 0 -> {
                navController.popBackStack()
                (context as Activity).finish()
            }

            else -> {
                cor.launch(Dispatchers.Main) {
                    pagerState.animateScrollToPage(0)
                }
            }
        }
    }
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
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            isFrequencyInChanging = true
                        }) {
                        Text(
                            text = stringResource(id = weekOfYear.resourceName),
                            fontSize = FontSize.big22,
                            color = ScheduleTheme.colors.oppositeTheme
                        )

                        Icon(
                            imageVector = if (isFrequencyInChanging) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = "Fold or Undolf list with frequency"
                        )
                    }
                    DropdownMenu(modifier = Modifier
                        .background(ScheduleTheme.colors.singleTheme)
                        .border(
                            border = BorderStroke(
                                width = 2.dp, color = ScheduleTheme.colors.oppositeTheme
                            )
                        ),
                        expanded = isFrequencyInChanging,
                        onDismissRequest = { isFrequencyInChanging = false }) {
                        DropdownMenuItem(text = {
                            Row {
                                TextForThisTheme(
                                    text = stringResource(id = Frequency.Numerator.resourceName),
                                    fontSize = FontSize.medium19
                                )
                                if (settings.isSelectedFrequencyCorrespondsToTheWeekNumbers != null && weekOfYear == Frequency.Numerator) Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = "This is selected or not",
                                    tint = ScheduleTheme.colors.oppositeTheme
                                )
                            }
                        }, onClick = {
                            chooseTypeOfDefinitionFrequencyDependsOn(selectedFrequency = Frequency.Numerator)
                        })

                        Spacer(
                            modifier = Modifier
                                .height(2.dp)
                                .padding(horizontal = 15.dp)
                                .background(color = ScheduleTheme.colors.oppositeTheme),
                        )

                        DropdownMenuItem(text = {
                            Row {
                                TextForThisTheme(
                                    text = stringResource(id = Frequency.Denominator.resourceName),
                                    fontSize = FontSize.medium19
                                )
                                if (settings.isSelectedFrequencyCorrespondsToTheWeekNumbers != null && weekOfYear == Frequency.Denominator) Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = "This is selected or not",
                                    tint = ScheduleTheme.colors.oppositeTheme
                                )
                            }

                        }, onClick = {
                            chooseTypeOfDefinitionFrequencyDependsOn(selectedFrequency = Frequency.Denominator)
                        })

                        Spacer(
                            modifier = Modifier
                                .height(2.dp)
                                .padding(horizontal = 15.dp)
                                .background(color = ScheduleTheme.colors.oppositeTheme),
                        )

                        DropdownMenuItem(text = {
                            Row {
                                TextForThisTheme(
                                    text = stringResource(id = R.string.auto),
                                    fontSize = FontSize.medium19
                                )
                                if (settings.isSelectedFrequencyCorrespondsToTheWeekNumbers == null) Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = "This is selected or not",
                                    tint = ScheduleTheme.colors.oppositeTheme
                                )
                            }
                        }, onClick = {
                            chooseTypeOfDefinitionFrequencyDependsOn(selectedFrequency = null)
                        })
                    }
                }
            }
        })
    }, bottomBar = {
        BottomBar(
            containerColor = mainColor, clickToScreen2 = {
                if (!isUpdateInProcess) navController.navigate(Screen.Settings.route)
            }, selected1 = true
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyRow(
                state = cardsWithDateState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                items(count = viewModel.pageNumber) { index ->
                    val day = viewModel.todayDate.plusDays(index.toLong())
                    Card(modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .clickable {
                            cor.launch(Dispatchers.Main) {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                        .alpha(
                            if (day != localDate && day != viewModel.todayDate) 0.5f else 1f
                        ), colors = CardDefaults.cardColors(
                        containerColor = if (day == viewModel.todayDate) mainColor
                        else ScheduleTheme.colors.buttonColor,
                        contentColor = (if (day == viewModel.todayDate) mainColor
                        else ScheduleTheme.colors.buttonColor).suitableColor()
                    )) {
                        Text(
                            text = day.getDateString(ruDaysOfWeek = viewModel.ruDaysOfWeek),
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
                    val dateOfThisLesson = viewModel.todayDate.plusDays(page.toLong())
                    val weekOfYearOfThisDay = dateOfThisLesson.getFrequencyByLocalDate()
                        .changeFrequencyIfDefinedInSettings(settings = settings)
                    if (!pagerState.isScrollInProgress) LaunchedEffect(key1 = null) {
                        cardsWithDateState.animateScrollToItem(pagerState.currentPage)
                    }
                    changeDateAndFrequency(daysAfterTodayDate = pagerState.currentPage.toLong())
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        val lessonsOfThisDay = lessons.filter {
                            it.dayOfWeek == dateOfThisLesson.dayOfWeek &&
                                    (it.frequency == null || it.frequency == weekOfYearOfThisDay) &&
                                    (it.subGroup == settings.subgroup || settings.subgroup == null || it.subGroup == null)
                        }.sorted()

                        val lessonsInOppositeNumAndDenDay = lessons.filter {
                            it.dayOfWeek == dateOfThisLesson.dayOfWeek &&
                                    it.frequency == weekOfYearOfThisDay.getOpposite() &&
                                    (it.subGroup == settings.subgroup || settings.subgroup == null || it.subGroup == null)
                        }.sorted()

                        if (lessonsOfThisDay.isNotEmpty()) {
                            viewModel.nowIsLesson = false
                            lessonsOfThisDay.forEach { lesson ->
                                if (lesson.nowIsLesson(localTime) && viewModel.todayDate == dateOfThisLesson) {
                                    viewModel.nowIsLesson = true
                                    lesson.StringForSchedule(
                                        colorBack = mainColor,
                                        dateOfThisLesson = null,
                                        viewModel = null
                                    )
                                } else if (viewModel.todayDate == dateOfThisLesson && lessonsOfThisDay.any {
                                        it.startTime > localTime
                                    } && lesson == lessonsOfThisDay.filter {
                                        it.startTime > localTime
                                    }[0] && !viewModel.nowIsLesson) {
                                    CardOfNextLesson(colorOfCard = mainColor) {
                                        lesson.StringForSchedule(
                                            colorBack = ScheduleTheme.colors.buttonColor,
                                            padding = 0.dp,
                                            dateOfThisLesson = dateOfThisLesson,
                                            viewModel =
                                            if (lesson.startTime.minusMinutes(
                                                    NotificationAboutLessonsSettings.minutesBeforeLessonForNotification
                                                )
                                                > localTime
                                            ) viewModel
                                            else null
                                        )

                                    }
                                } else lesson.StringForSchedule(
                                    colorBack = ScheduleTheme.colors.buttonColor,
                                    dateOfThisLesson = dateOfThisLesson,
                                    viewModel = if (
                                        lesson.startTime.minusMinutes(
                                            NotificationAboutLessonsSettings.minutesBeforeLessonForNotification
                                        ) > localTime &&
                                        viewModel.todayDate == dateOfThisLesson ||
                                        viewModel.todayDate != dateOfThisLesson
                                    ) viewModel
                                    else null
                                )
                            }
                        } else WeekDay(context = context, modifier = Modifier.let {
                            var modifier = Modifier.padding(vertical = 75.dp)
                            if (lessonsInOppositeNumAndDenDay.isEmpty()) modifier =
                                Modifier.weight(1f)
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
                                text = "${stringResource(id = R.string.other_lessons_in_this_day)} ${
                                    stringResource(
                                        id = weekOfYearOfThisDay.getOpposite().resourceName
                                    )
                                }",
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                fontSize = FontSize.big22
                            )
                            lessonsInOppositeNumAndDenDay.forEach { lesson ->
                                lesson.StringForSchedule(
                                    colorBack = ScheduleTheme.colors.buttonColor,
                                    lessonOfThisNumAndDenOrNot = false,
                                    dateOfThisLesson = null,
                                    viewModel = null
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