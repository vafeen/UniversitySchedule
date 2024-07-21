package ru.vafeen.universityschedule.ui.components.ui_utils

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ru.vafeen.universityschedule.R
import ru.vafeen.universityschedule.ui.theme.FontSize
import ru.vafeen.universityschedule.ui.theme.ScheduleTheme
import ru.vafeen.universityschedule.utils.Link
import ru.vafeen.universityschedule.utils.SharedPreferencesValue
import ru.vafeen.universityschedule.utils.copyTextToClipBoard
import ru.vafeen.universityschedule.utils.linkIsEmpty
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
    val iconsSize = 30.dp
    Dialog(
        onDismissRequest = { onDismissRequest() }, properties = DialogProperties()
    ) {
        Card(
            colors =
            CardDefaults.cardColors(containerColor = ScheduleTheme.colors.oppositeTheme)
        ) {
            Card(
                modifier = Modifier
                    .padding(3.dp),

                colors = CardDefaults.cardColors(containerColor = ScheduleTheme.colors.singleTheme)
            ) {
                Column(
                    modifier = Modifier

                        .height(250.dp), verticalArrangement = Arrangement.SpaceAround
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
                            .padding(horizontal = 5.dp),
                        fontSize = FontSize.medium
                    )


                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (!textLink.linkIsEmpty(emptyLink = noLink)) {
                            IconButton(
                                onClick = {
                                    if (textLink.linkIsEmpty(emptyLink = noLink)) context.copyTextToClipBoard(
                                        text = textLink
                                    )
                                }
                            ) {
                                Icon(
                                    modifier = Modifier.size(iconsSize),
                                    painter = painterResource(id = R.drawable.copy),
                                    contentDescription = "copy",
                                    tint = ScheduleTheme.colors.oppositeTheme
                                )
                            }
                            IconButton(
                                onClick = {
                                    textLink = noLink
                                    pref.edit().apply {
                                        remove(SharedPreferencesValue.Link.key)
                                        apply()
                                    }
                                }
                            ) {
                                Icon(
                                    modifier = Modifier.size(iconsSize),
                                    painter = painterResource(id = R.drawable.clear),
                                    contentDescription = "clear",
                                    tint = ScheduleTheme.colors.oppositeTheme
                                )
                            }
                        }
                        IconButton(onClick = {
                            context.pasteText()?.let {
                                val contains = it.contains("docs.google.com/spreadsheets/")
                                if (contains)
                                    pref.edit().apply {
                                        textLink = if (!it.contains(Link.PROTOCOL))
                                            "${Link.PROTOCOL}$it" else it
                                        putString(
                                            SharedPreferencesValue.Link.key,
                                            textLink
                                        )
                                        apply()
                                    }
                                else
                                    Toast.makeText(
                                        context,
                                        context.getString(R.string.its_no_google_sheets_link),
                                        Toast.LENGTH_SHORT
                                    ).show()

                            }
                        }) {
                            Icon(
                                modifier = Modifier.size(iconsSize),
                                painter = painterResource(id = R.drawable.paste),
                                contentDescription = "paste",
                                tint = ScheduleTheme.colors.oppositeTheme
                            )
                        }
                    }

                }
            }
        }


    }
}