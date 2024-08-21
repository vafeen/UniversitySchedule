package ru.vafeen.universityschedule.ui.components.ui_utils

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.raedapps.alwan.rememberAlwanState
import com.raedapps.alwan.ui.Alwan
import ru.vafeen.universityschedule.R
import ru.vafeen.universityschedule.ui.components.bottom_bar.BottomBar
import ru.vafeen.universityschedule.ui.theme.FontSize
import ru.vafeen.universityschedule.ui.theme.ScheduleTheme
import ru.vafeen.universityschedule.utils.suitableColor

@Composable
fun ColorPickerDialog(
    context: Context,
    firstColor: Color,
    onDismissRequest: () -> Unit,
    onChangeColorCallback: (Color) -> Unit
) {
    val defaultColor = ScheduleTheme.colors.mainColor

    val colorState = rememberAlwanState(initialColor = firstColor)
    var newColor by remember {
        mutableStateOf(firstColor)
    }


    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = ScheduleTheme.colors.singleTheme
            ),
            border = BorderStroke(width = 2.dp, color = ScheduleTheme.colors.oppositeTheme)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = context.getString(R.string.new_interface_color),
                    fontSize = FontSize.big22,
                    modifier = Modifier.padding(10.dp),
                    color = ScheduleTheme.colors.oppositeTheme
                )

                BottomBar(
                    containerColor = newColor,
                    selected2 = true,
                )

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
                            text = stringResource(R.string.cancel),
                            color = newColor.suitableColor()
                        )
                    }
                    if (firstColor != defaultColor)
                        IconButton(onClick = {
                            onChangeColorCallback(defaultColor)
                            onDismissRequest()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "delete this theme",
                                tint = ScheduleTheme.colors.oppositeTheme
                            )
                        }
                    ColorPickerDialogButton(
                        enabled = newColor.toArgb() != firstColor.toArgb(),
                        color = newColor,
                        onClick = {
                            onChangeColorCallback(newColor)
                            onDismissRequest()
                        }) {
                        Text(
                            text = stringResource(R.string.apply),
                            color = newColor.suitableColor()
                        )
                    }
                }

            }
        }


    }

}