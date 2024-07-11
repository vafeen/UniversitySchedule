package ru.vafeen.universityschedule.ui.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.raedapps.alwan.rememberAlwanState
import com.raedapps.alwan.ui.Alwan
import ru.vafeen.universityschedule.R
import ru.vafeen.universityschedule.ui.theme.FontSize
import ru.vafeen.universityschedule.ui.theme.ScheduleTheme
import ru.vafeen.universityschedule.utils.SharedPreferencesValue
import ru.vafeen.universityschedule.utils.suitableColor

@Composable
fun ColorPickerDialog(
    context: Context,
    onDismissRequest: () -> Unit,
    onChangeColorCallback: (Color) -> Unit
) {
    val defaultColor = ScheduleTheme.colors.mainColor

    val firstColor = Color(
        context.getSharedPreferences(
            SharedPreferencesValue.Name.key, Context.MODE_PRIVATE
        ).getInt(SharedPreferencesValue.Color.key, defaultColor.toArgb())
    )
    val colorState = rememberAlwanState(initialColor = firstColor)
    val pref = context.getSharedPreferences(
        SharedPreferencesValue.Name.key, Context.MODE_PRIVATE
    )

    var newColor by remember {
        mutableStateOf(firstColor)
    }


    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Log.d("color", "xxxColor =${newColor.toArgb()}")
        Column(
            modifier = Modifier
                .background(ScheduleTheme.colors.singleTheme)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = context.getString(R.string.new_interface_color), fontSize = FontSize.huge,
                modifier = Modifier.padding(10.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
                    .background(newColor)
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Icon1",
                    tint = newColor.suitableColor()
                )
                Icon(
                    Icons.AutoMirrored.Filled.List,
                    contentDescription = "Icon2",
                    tint = newColor.suitableColor()
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
// color picker
            Alwan(
                onColorChanged = {
                    newColor = it
                },
                modifier = Modifier.size(300.dp),
                state = colorState,
            )

            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ColorPickerDialogButton(
                    onClick = onDismissRequest,
                    color = newColor,
                ) {
                    Text(
                        text = "cancel",
                        color = newColor.suitableColor()
                    )
                }

                ColorPickerDialogButton(
                    enabled = newColor.toArgb() != firstColor.toArgb(),
                    color = newColor,
                    onClick = {
                        pref.edit().apply {
                            putInt(
                                SharedPreferencesValue.Color.key,
                                newColor.toArgb()
                            )
                            apply()
                        }
                        onChangeColorCallback(newColor)
                        onDismissRequest()
                    }) {
                    Text(
                        text = "apply",
                        color = newColor.suitableColor()
                    )
                }
            }

        }

    }

}