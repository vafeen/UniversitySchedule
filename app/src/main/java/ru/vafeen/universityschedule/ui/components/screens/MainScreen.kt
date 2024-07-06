package ru.vafeen.universityschedule.ui.components.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import ru.vafeen.universityschedule.ui.components.viewModels.MainScreenViewModel
import java.time.LocalDateTime


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    navController: NavController, viewModel: MainScreenViewModel = viewModel(
        modelClass = MainScreenViewModel::class.java
    )
) {
    var localDateTime by remember {
        mutableStateOf(LocalDateTime.now())
    }

    val pagerState = rememberPagerState(
        pageCount = {
            7
        }, initialPage = localDateTime.dayOfWeek.value - 1
    )
    var nowIsLesson: Boolean
    LaunchedEffect(key1 = null) {
        withContext(Dispatchers.Main) {
            while (true) {
                localDateTime = LocalDateTime.now()
                delay(1000L)
            }
        }
    }
    Scaffold(

    ) { paddingValues: PaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
        ) {
            Text(text = "hello world")
        }
    }

}