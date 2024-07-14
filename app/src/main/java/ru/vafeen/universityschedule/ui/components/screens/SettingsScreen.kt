package ru.vafeen.universityschedule.ui.components.screens

import android.content.Context
import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vafeen.universityschedule.R
import ru.vafeen.universityschedule.ui.components.TextForThisTheme
import ru.vafeen.universityschedule.ui.components.bottom_bar.BottomBar
import ru.vafeen.universityschedule.ui.components.ui_utils.ColorPickerDialog
import ru.vafeen.universityschedule.ui.components.ui_utils.EditLinkDialog
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
    context: Context,
    navController: NavController,
    viewModel: SettingsScreenViewModel,
) {
    val defaultColor = ScheduleTheme.colors.mainColor
    var mainColor by remember {
        mutableStateOf(
            Color(
                context.getSharedPreferences(
                    SharedPreferencesValue.Name.key, Context.MODE_PRIVATE
                ).getInt(SharedPreferencesValue.Color.key, defaultColor.toArgb())
            )
        )
    }
    var linkIsEditable by remember {
        mutableStateOf(false)
    }
    var colorIsEditable by remember {
        mutableStateOf(false)
    }
    val gotoMainScreenCallBack = {
        navController.popBackStack()
        navController.popBackStack()
        navController.navigate(Screen.Main.route)
    }

    var textLink by remember {
        mutableStateOf(viewModel.getLinkCallBack())
    }
    BackHandler(onBack = gotoMainScreenCallBack)
    Scaffold(
        containerColor = ScheduleTheme.colors.singleTheme,
        bottomBar = {
            BottomBar(
                containerColor = mainColor,
                clickToScreen1 = gotoMainScreenCallBack,
                selected2 = true
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (linkIsEditable)
                EditLinkDialog(context = context) {
                    textLink = viewModel.getLinkCallBack()
                    linkIsEditable = false
                }
            if (colorIsEditable)
                ColorPickerDialog(
                    context = context,
                    onDismissRequest = { colorIsEditable = false }) {
                    mainColor = it
                }

            TextForThisTheme(
                text = stringResource(R.string.settings),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = FontSize.medium
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
                            fontSize = FontSize.small,
                            text = stringResource(R.string.link_to_table),
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
                if (textLink != viewModel.noLink) {
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
                                fontSize = FontSize.small,
                                text = stringResource(R.string.table),
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
                            fontSize = FontSize.small, text = stringResource(R.string.code)
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
                            fontSize = FontSize.small, text = stringResource(R.string.license)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.book),
                            contentDescription = "read license",
                            tint = ScheduleTheme.colors.oppositeTheme
                        )
                    }
                }
                Spacer(modifier = Modifier.height(viewModel.spaceBetweenCards))

                // Color
                Card(colors = cardColors) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                colorIsEditable = true
                            }
                            .padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextForThisTheme(
                            modifier = Modifier.padding(10.dp),
                            fontSize = FontSize.small,
                            text = stringResource(R.string.interface_color)
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.palette),
                            contentDescription = "read license",
                            tint = ScheduleTheme.colors.oppositeTheme
                        )
                    }
                }
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
                    fontSize = FontSize.small,
                    text = "${stringResource(R.string.version)} ${getVersionName(context = LocalContext.current)}"
                )
            }
        }
    }
}