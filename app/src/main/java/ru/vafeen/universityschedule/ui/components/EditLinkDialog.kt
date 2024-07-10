package ru.vafeen.universityschedule.ui.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ru.vafeen.universityschedule.R
import ru.vafeen.universityschedule.ui.theme.FontSize
import ru.vafeen.universityschedule.ui.theme.ScheduleTheme
import ru.vafeen.universityschedule.utils.Link
import ru.vafeen.universityschedule.utils.SharedPreferencesValue
import ru.vafeen.universityschedule.utils.copyTextToClipBoard
import ru.vafeen.universityschedule.utils.pasteText

@Composable
fun EditLinkDialog(context: Context, onDismissRequest: () -> Unit) {
    val noLink = context.getString(R.string.no_link)
    var textLink by remember {
        mutableStateOf(noLink)
    }
    val pref = context.getSharedPreferences(
        SharedPreferencesValue.Name.key, Context.MODE_PRIVATE
    )
    textLink = pref.getString(SharedPreferencesValue.Link.key, noLink) ?: noLink
    Dialog(
        onDismissRequest = { onDismissRequest() }, properties = DialogProperties()
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(3.dp)
                .background(ScheduleTheme.colors.singleTheme)
                .height(250.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .align(Alignment.End),
                onClick = { onDismissRequest() },
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = Icons.Default.Close,
                    contentDescription = "close",
                    tint = ScheduleTheme.colors.oppositeTheme
                )
            }

            TextForThisTheme(
                text = textLink,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(1f)
                    .scrollable(rememberScrollState(), orientation = Orientation.Vertical),
                fontSize = FontSize.huge
            )


            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        if (textLink.isNotEmpty()) context.copyTextToClipBoard(text = textLink)
                        onDismissRequest()
                    }, enabled = textLink.isNotEmpty()
                ) {
                    Text(text = stringResource(R.string.copy))
                }
                Button(
                    onClick = {
                        pref.edit().apply {
                            remove(SharedPreferencesValue.Link.key)
                            apply()
                        }
                        onDismissRequest()
                    }, enabled = textLink.isNotEmpty()
                ) {
                    Text(text = stringResource(R.string.clear))
                }
                Button(onClick = {
                    textLink = context.pasteText()?.let {
                        if (it.contains("docs.google.com/spreadsheets/") &&
                            !it.contains(Link.PROTOCOL)
                        ) "${Link.PROTOCOL}$it" else it
                    } ?: textLink
                    pref.edit().apply {
                        putString(SharedPreferencesValue.Link.key, textLink)
                        apply()
                    }
                    onDismissRequest()
                }) {
                    Text(text = stringResource(R.string.paste))
                }
            }

        }
    }
}