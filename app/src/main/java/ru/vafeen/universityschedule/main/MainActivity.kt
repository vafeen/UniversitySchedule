package ru.vafeen.universityschedule.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vafeen.universityschedule.ui.theme.UniversityScheduleTheme
import ru.vafeen.universityschedule.utils.createGSheetsService


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val id = "1pCc0c0tYU8RfCusoAuvT45J7bsz1vZxZXsQIfgCfhL4"

    private val gSheetsService = createGSheetsService(id = id)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var text by remember {
                mutableStateOf("")
            }
            UniversityScheduleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues = innerPadding),
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(text = text)
                        Button(onClick = {
                            lifecycleScope.launch(Dispatchers.IO) {
                                val response = gSheetsService.getSheetData().string()
                                if (response.isNotEmpty()) {
                                    text = response
                                    Log.d("retrofit", response)
                                }
                            }
                        }) {
                            Text(text = "get data")
                        }
                    }
                }
            }
        }
    }
}