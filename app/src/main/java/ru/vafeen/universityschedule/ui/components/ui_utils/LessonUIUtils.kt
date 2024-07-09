package ru.vafeen.universityschedule.ui.components.ui_utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.vafeen.universityschedule.R
import ru.vafeen.universityschedule.database.entity.Lesson
import ru.vafeen.universityschedule.ui.theme.FontSize
import ru.vafeen.universityschedule.ui.theme.ScheduleTheme
import java.time.LocalTime

@Composable
fun Lesson.StringForSchedule(
    colorBack: Color,
    lessonOfThisNumAndDenOrNot: Boolean = true,
    padding: Dp = 10.dp,
) {
    Card(
        modifier = Modifier
            .padding(padding)//.clickable {  }
            .fillMaxWidth()
            .alpha(
                if (lessonOfThisNumAndDenOrNot) 1.0f
                else 0.55f
            ),
        colors = CardDefaults.cardColors(
            containerColor = colorBack
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.schedule),
                    contentDescription = "Icon teacher",
                    tint = ScheduleTheme.colors.oppositeTheme
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = getLessonTimeString(), fontSize = FontSize.medium,
                    color = ScheduleTheme.colors.oppositeTheme
                )
                if (classroom.isNotEmpty()) {
                    Spacer(modifier = Modifier.width(7.dp))
                    Row {
                        Icon(
                            imageVector = Icons.Default.Place,
                            contentDescription = "Icon classroom",
                            tint = ScheduleTheme.colors.oppositeTheme
                        )

                        Text(
                            text = classroom,
                            color = ScheduleTheme.colors.oppositeTheme,
                            fontSize = FontSize.medium
                        )
                    }
                }
//                if (typeOfLesson.myIsNotEmpty()) { TODO("ADD TYPE OF LESSON")
//                    Spacer(modifier = Modifier.width(17.dp))
//                    Text(
//                        modifier = Modifier.alpha(0.5f),
//                        text = typeOfLesson,
//                        color = ScheduleTheme.colors.text,
//                        fontSize = FontSize.medium
//                    )
//                }
            }

            if (name.isNotEmpty()) {
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = name, color = ScheduleTheme.colors.oppositeTheme, fontSize = 20.sp)
            }
            if (teacher.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Icon teacher",
                        tint = ScheduleTheme.colors.oppositeTheme,
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = teacher.replace(oldValue = " ", newValue = "\n"),
                        fontSize = 17.sp,
                        color = ScheduleTheme.colors.oppositeTheme,
                        maxLines = 3
                    )
                }
            }
        }


    }
}

fun LocalTime.toLessonTime(): String = "${hour}:" + if (minute < 10) "0${minute}" else "$minute"


fun Lesson.getLessonTimeString(): String =
    this.let { "${it.startTime.toLessonTime()} - ${it.endTime.toLessonTime()}" }