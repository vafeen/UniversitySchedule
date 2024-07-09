package ru.vafeen.universityschedule.ui.components.screens

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ru.vafeen.universityschedule.R
import ru.vafeen.universityschedule.ui.components.EditLinkDialog
import ru.vafeen.universityschedule.ui.components.TextForThisTheme
import ru.vafeen.universityschedule.ui.components.bottom_bar.BottomBar
import ru.vafeen.universityschedule.ui.components.viewModels.SettingsScreenViewModel
import ru.vafeen.universityschedule.ui.navigation.Screen
import ru.vafeen.universityschedule.ui.theme.FontSize
import ru.vafeen.universityschedule.ui.theme.ScheduleTheme
import ru.vafeen.universityschedule.utils.Link
import ru.vafeen.universityschedule.utils.SharedPreferencesValue
import ru.vafeen.universityschedule.utils.getVersionName
import ru.vafeen.universityschedule.utils.openLink

/**
 * Screen with settings for application:
 * 1. Link (opening by tap and opening editing dialog by tap on pencil near
 * 2. License
 * 3. Version
 * 4. Page in RUStore
 */
@Composable
fun SettingsScreen(
    navController: NavController,
    context: Context,
    viewModel: SettingsScreenViewModel = viewModel(
        modelClass = SettingsScreenViewModel::class.java
    ),
) {
    val noLink = context.getString(R.string.no_link)
    val pref = context.getSharedPreferences(
        SharedPreferencesValue.Name.key, Context.MODE_PRIVATE
    )
    val getLinkCallBack = { pref.getString(SharedPreferencesValue.Link.key, noLink) ?: noLink }
    var linkIsEditable by remember {
        mutableStateOf(false)
    }


    var textLink by remember {
        mutableStateOf(getLinkCallBack())
    }

    Scaffold(
        containerColor = ScheduleTheme.colors.singleTheme,
        bottomBar = {
            BottomBar(
                clickToScreen1 = {
                    navController.popBackStack()
                    navController.popBackStack()
                    navController.navigate(Screen.Main.route)
                }, selected2 = true
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (linkIsEditable)
                EditLinkDialog(context = context) {
                    textLink = getLinkCallBack()
                    linkIsEditable = false
                }

            TextForThisTheme(
                text = "Настройки",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = FontSize.huge
            )
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier = Modifier
                    .weight(1f)

                    .padding(horizontal = 40.dp),
            ) {

                val cardColors = CardDefaults.cardColors(
                    containerColor = ScheduleTheme.colors.buttonColor,
                )

                // Edit link
                Card(colors = cardColors) {
                    Row(
                        modifier = Modifier
                            .clickable { linkIsEditable = true }
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextForThisTheme(
                            modifier = Modifier.padding(10.dp),
                            fontSize = FontSize.medium,
                            text = "Ссылка на таблицу",
                        )
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "edit link",
                            tint = ScheduleTheme.colors.oppositeTheme
                        )
                    }
                }
                Spacer(modifier = Modifier.height(viewModel.spaceBetweenCards))

                // View table
                if (textLink != noLink) {
                    Card(colors = cardColors) {
                        Row(
                            modifier = Modifier
                                .clickable {
                                    openLink(context = context, link = textLink)
                                }
                                .padding(horizontal = 10.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextForThisTheme(
                                modifier = Modifier.padding(10.dp),
                                fontSize = FontSize.medium,
                                text = "Таблица",
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.table),
                                contentDescription = "edit link",
                                tint = ScheduleTheme.colors.oppositeTheme
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(viewModel.spaceBetweenCards))
                }
                // CODE
                Card(colors = cardColors) {
                    Row(modifier = Modifier
                        .clickable {
                            openLink(
                                context = context,
                                link = Link.CODE
                            )
                        }
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        TextForThisTheme(
                            modifier = Modifier.padding(10.dp),
                            fontSize = FontSize.medium, text = "Код"
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.terminal),
                            contentDescription = "view code",
                            tint = ScheduleTheme.colors.oppositeTheme
                        )
                    }
                }
                Spacer(modifier = Modifier.height(viewModel.spaceBetweenCards))

                // LICENSE
                Card(colors = cardColors) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                openLink(
                                    context = context,
                                    link = Link.LICENSE
                                )
                            }
                            .padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextForThisTheme(
                            modifier = Modifier.padding(10.dp),
                            fontSize = FontSize.medium, text = "License"
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.book),
                            contentDescription = "read license",
                            tint = ScheduleTheme.colors.oppositeTheme
                        )
                    }
                }
                Spacer(modifier = Modifier.height(viewModel.spaceBetweenCards))
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextForThisTheme(
                    modifier = Modifier
                        .padding(10.dp),
                    fontSize = FontSize.medium,
                    text = "version ${getVersionName(context = LocalContext.current)}"
                )
            }
        }
    }
}