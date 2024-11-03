package ru.vafeen.universityschedule.presentation.components.ui_utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.presentation.components.viewModels.MainScreenViewModel
import ru.vafeen.universityschedule.presentation.theme.FontSize
import ru.vafeen.universityschedule.presentation.utils.getLessonTimeString
import ru.vafeen.universityschedule.presentation.utils.suitableColor
import java.time.LocalDate
import ru.vafeen.universityschedule.resources.R as DR

@Composable
internal fun Lesson.StringForSchedule(
    viewModel: MainScreenViewModel?,
    dateOfThisLesson: LocalDate?,
    colorBack: Color
) {

    val suitableColor = colorBack.suitableColor()
    var notificationsIsEditing by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colorBack
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = DR.drawable.schedule),
                        contentDescription = "Icon schedule",
                        tint = suitableColor
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = getLessonTimeString(),
                        fontSize = FontSize.small17,
                        color = suitableColor
                    )
                }
                if (classroom?.isNotEmpty() == true) {
                    Spacer(modifier = Modifier.width(5.dp))
                    Row {
                        Icon(
                            imageVector = Icons.Default.Place,
                            contentDescription = "Icon classroom",
                            tint = suitableColor
                        )

                        Text(
                            text = classroom ?: undefined,
                            color = suitableColor,
                            fontSize = FontSize.small17
                        )
                    }
                }
                if (viewModel != null && dateOfThisLesson != null) {
                    if (notificationsIsEditing)
                        this@StringForSchedule.ReminderDialog(
                            onDismissRequest = { notificationsIsEditing = false },
                            viewModel = viewModel,
                            thisDate = dateOfThisLesson
                        )
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Row(
                            modifier = Modifier
                                .clickable {
                                    notificationsIsEditing = true
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = if (idOfReminderBeforeLesson != null && idOfReminderAfterBeginningLesson != null) DR.drawable.edit else DR.drawable.add),
                                contentDescription = "Edit notifications about this lesson",
                                tint = suitableColor
                            )
                            if (idOfReminderBeforeLesson != null)
                                Icon(
                                    painter = painterResource(id = DR.drawable.message),
                                    contentDescription = "Message about lesson",
                                    tint = suitableColor
                                )
                            if (idOfReminderAfterBeginningLesson != null)
                                Icon(
                                    painter = painterResource(id = DR.drawable.notification_about_checking),
                                    contentDescription = "Message about checking on this lesson",
                                    tint = suitableColor
                                )
                        }
                    }
                }
            }

            name?.let {
                if (it.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = it, color = suitableColor, fontSize = FontSize.big22
                    )
                }
            }



            if (teacher?.isNotEmpty() == true) Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                Icon(
                    painter = painterResource(id = DR.drawable.person),
                    contentDescription = "Icon teacher",
                    tint = suitableColor
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = teacher?.replace(oldValue = " ", newValue = "\n") ?: undefined,
                    fontSize = FontSize.small17,
                    color = suitableColor,
                    maxLines = 3
                )
            }

            if (subGroup?.isNotEmpty() == true) Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    painter = painterResource(id = DR.drawable.group),
                    contentDescription = "Icon subgroup",
                    tint = suitableColor
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = subGroup ?: undefined,
                    fontSize = FontSize.small17,
                    color = suitableColor,
                    maxLines = 3
                )
            }
        }
    }
}


