package ru.vafeen.universityschedule.ui.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.mhssn.colorpicker.ColorPicker
import io.mhssn.colorpicker.ColorPickerType
import ru.vafeen.universityschedule.ui.theme.ScheduleTheme
import ru.vafeen.universityschedule.utils.SharedPreferencesValue

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ColorPickerDialog(
    context: Context,
    onDismissRequest: () -> Unit,
    onChangeColorCallback: (Color) -> Unit
) {

    val pref = context.getSharedPreferences(
        SharedPreferencesValue.Name.key, Context.MODE_PRIVATE
    )
    val defaultColor = ScheduleTheme.colors.mainColor
    var color by remember {
        mutableStateOf(
            Color(
                context.getSharedPreferences(
                    SharedPreferencesValue.Name.key, Context.MODE_PRIVATE
                ).getInt(SharedPreferencesValue.Color.key, defaultColor.toArgb())
            )
        )
    }

    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .background(ScheduleTheme.colors.singleTheme)
        ) {
            ColorPicker(
                modifier = Modifier.padding(20.dp),
                type = ColorPickerType.Classic()
            ) {
                color = it
            }
            Icon(
                imageVector = Icons.Default.CheckCircle, contentDescription = "",
                tint = color
            )
            Button(onClick = {
                pref.edit().apply {
                    putInt(
                        SharedPreferencesValue.Color.key,
                        color.toArgb()
                    )
                    apply()
                }
                onChangeColorCallback(color)
                onDismissRequest()
            }) {
                Text(text = "apply")
            }
        }

    }

}