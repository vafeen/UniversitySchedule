package ru.vafeen.universityschedule.presentation.components.screens

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.vafeen.universityschedule.domain.utils.getMainColorForThisTheme
import ru.vafeen.universityschedule.domain.utils.getVersionName
import ru.vafeen.universityschedule.presentation.components.bottom_bar.BottomBar
import ru.vafeen.universityschedule.presentation.components.ui_utils.CardOfSettings
import ru.vafeen.universityschedule.presentation.components.ui_utils.ColorPickerDialog
import ru.vafeen.universityschedule.presentation.components.ui_utils.EditLinkDialog
import ru.vafeen.universityschedule.presentation.components.ui_utils.TextForThisTheme
import ru.vafeen.universityschedule.presentation.components.video.AssetsInfo
import ru.vafeen.universityschedule.presentation.components.video.GifPlayer
import ru.vafeen.universityschedule.presentation.components.viewModels.SettingsScreenViewModel
import ru.vafeen.universityschedule.presentation.navigation.Screen
import ru.vafeen.universityschedule.presentation.theme.FontSize
import ru.vafeen.universityschedule.presentation.theme.Theme
import ru.vafeen.universityschedule.presentation.utils.Link
import ru.vafeen.universityschedule.presentation.utils.getIconByRequestStatus
import ru.vafeen.universityschedule.presentation.utils.openLink
import ru.vafeen.universityschedule.presentation.utils.sendEmail
import ru.vafeen.universityschedule.presentation.utils.suitableColor
import ru.vafeen.universityschedule.resources.R as DR
import ru.vafeen.universityschedule.presentation.R as PR

/**
 * Screen with settings for application:
 *
 * General:
 * - Link
 * - Table
 * - Interface color
 * - Subgroup
 *
 * Contacts:
 * - Code
 * - Report a bug
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsScreen(
    navController: NavController,
) {
    val viewModel: SettingsScreenViewModel = koinViewModel()
    val context = LocalContext.current
    val dark = isSystemInDarkTheme()
    var subgroupList by remember { mutableStateOf(listOf<String>()) }
    val settings by viewModel.settings.collectAsState()
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
    val checkBoxColors = CheckboxDefaults.colors(
        checkedColor = Theme.colors.oppositeTheme,
        checkmarkColor = Theme.colors.singleTheme,
        uncheckedColor = Theme.colors.oppositeTheme
    )
    var subGroupIsChanging by remember {
        mutableStateOf(false)
    }
    var catsOnUIIsChanging by remember {
        mutableStateOf(false)
    }
    val subGroupLazyRowState = rememberLazyListState()
    val networkState by viewModel.gSheetsServiceRequestStatusFlow.collectAsState()

    LaunchedEffect(key1 = null) {
        viewModel.subgroupFlow.collect {
            subgroupList = it
        }
    }

    BackHandler(onBack = gotoMainScreenCallBack)
    Scaffold(
        containerColor = Theme.colors.singleTheme,
        topBar = {
            TopAppBar(colors = TopAppBarColors(
                containerColor = Theme.colors.singleTheme,
                scrolledContainerColor = Theme.colors.singleTheme,
                navigationIconContentColor = Theme.colors.oppositeTheme,
                titleContentColor = Theme.colors.oppositeTheme,
                actionIconContentColor = Theme.colors.singleTheme
            ), modifier = Modifier.fillMaxWidth(), title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(
                            id = getIconByRequestStatus(
                                networkState = networkState
                            )
                        ),
                        contentDescription = "data updating state",
                        tint = Theme.colors.oppositeTheme
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    TextForThisTheme(
                        text = stringResource(PR.string.settings), fontSize = FontSize.big22
                    )
                }
            })
        },
        bottomBar = {
            BottomBar(
                containerColor = settings.getMainColorForThisTheme(isDark = dark)
                    ?: Theme.colors.mainColor,
                clickToScreen1 = gotoMainScreenCallBack,
                selected2 = true
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (linkIsEditable) EditLinkDialog(context = context) {
                linkIsEditable = false
            }
            if (colorIsEditable) ColorPickerDialog(context = context,
                firstColor = settings.getMainColorForThisTheme(isDark = dark)
                    ?: Theme.colors.mainColor,
                onDismissRequest = { colorIsEditable = false }) {
                viewModel.saveSettingsToSharedPreferences(
                    if (dark) settings.copy(
                        darkThemeColor = it
                    ) else settings.copy(lightThemeColor = it)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    // name of section
                    TextForThisTheme(
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.Center),
                        fontSize = FontSize.big22,
                        text = stringResource(PR.string.general)
                    )
                    if (settings.catInSettings)
                        GifPlayer(
                            size = 80.dp,
                            modifier = Modifier.align(Alignment.CenterEnd),
                            imageUri = Uri.parse(AssetsInfo.FUNNY_SETTINGS_CAT)
                        )
                }
                // Edit link
                CardOfSettings(
                    text = stringResource(PR.string.link_to_table),
                    icon = {
                        Icon(
                            painter = painterResource(id = DR.drawable.link),
                            contentDescription = "edit link",
                            tint = it.suitableColor()
                        )
                    }, onClick = { linkIsEditable = true }
                )

                // View table
                if (settings.link != null) {
                    CardOfSettings(
                        text = stringResource(PR.string.table),
                        icon = {
                            Icon(
                                painter = painterResource(id = DR.drawable.table),
                                contentDescription = "edit link",
                                tint = it.suitableColor()
                            )
                        },
                        onClick = { settings.link?.let { context.openLink(link = it) } }
                    )

                }

                // Subgroup
                if (subgroupList.isNotEmpty()) {
                    CardOfSettings(
                        text = stringResource(PR.string.subgroup),
                        icon = {
                            Icon(
                                painter = painterResource(id = DR.drawable.group),
                                contentDescription = "subgroup",
                                tint = it.suitableColor()
                            )
                        }, onClick = { subGroupIsChanging = !subGroupIsChanging },
                        additionalContentIsVisible = subGroupIsChanging,
                        additionalContent = {
                            LazyRow(
                                state = subGroupLazyRowState, modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = it)
                            ) {
                                items(subgroupList) { subgroup ->
                                    AssistChip(
                                        leadingIcon = {
                                            if (subgroup == settings.subgroup) Icon(
                                                imageVector = Icons.Default.Done,
                                                contentDescription = "this is user subgroup",
                                                tint = Theme.colors.oppositeTheme
                                            )
                                        },
                                        modifier = Modifier.padding(horizontal = 3.dp),
                                        onClick = {
                                            viewModel.saveSettingsToSharedPreferences(
                                                settings.copy(
                                                    subgroup = if (settings.subgroup != subgroup) subgroup else null
                                                )
                                            )
                                        },
                                        label = { TextForThisTheme(text = subgroup) },
                                    )
                                }
                            }
                        }
                    )

                }

                // name of section
                TextForThisTheme(
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally),
                    fontSize = FontSize.big22,
                    text = stringResource(PR.string.interface_str)
                )
                // Color
                CardOfSettings(
                    text = stringResource(PR.string.interface_color),
                    icon = {
                        Icon(
                            painter = painterResource(id = DR.drawable.palette),
                            contentDescription = "change color of interface",
                            tint = it.suitableColor()
                        )
                    }, onClick = { colorIsEditable = true }
                )
                // cats in interface 
                CardOfSettings(
                    text = stringResource(PR.string.cats_on_ui),
                    icon = {
                        Icon(
                            painter = painterResource(id = DR.drawable.cat),
                            contentDescription = "cats in interface",
                            tint = it.suitableColor()
                        )
                    }, onClick = { catsOnUIIsChanging = !catsOnUIIsChanging },
                    additionalContentIsVisible = catsOnUIIsChanging,
                    additionalContent = {
                        Column {
                            val onCheckedChangeWeekendCat = {
                                viewModel.saveSettingsToSharedPreferences(
                                    settings = settings.copy(
                                        weekendCat = !settings.weekendCat
                                    )
                                )
                            }
                            val onCheckedChangeCatInSettings = {
                                viewModel.saveSettingsToSharedPreferences(
                                    settings = settings.copy(
                                        catInSettings = !settings.catInSettings
                                    )
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onCheckedChangeWeekendCat() }
                                    .padding(horizontal = it),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextForThisTheme(
                                    text = stringResource(PR.string.weekend_cat),
                                    fontSize = FontSize.medium19
                                )
                                Checkbox(
                                    checked = settings.weekendCat,
                                    onCheckedChange = {
                                        onCheckedChangeWeekendCat()
                                    }, colors = checkBoxColors
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onCheckedChangeCatInSettings() }
                                    .padding(horizontal = it),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextForThisTheme(
                                    text = stringResource(PR.string.cat_in_settings),
                                    fontSize = FontSize.medium19
                                )
                                Checkbox(
                                    checked = settings.catInSettings,
                                    onCheckedChange = {
                                        onCheckedChangeCatInSettings()
                                    }, colors = checkBoxColors
                                )
                            }

                        }
                    }
                )


                // name of section
                TextForThisTheme(
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally),
                    fontSize = FontSize.big22,
                    text = stringResource(PR.string.contacts)
                )

                // CODE
                CardOfSettings(
                    text = stringResource(PR.string.code),
                    icon = {
                        Icon(
                            painter = painterResource(id = DR.drawable.terminal),
                            contentDescription = "view code",
                            tint = it.suitableColor()
                        )
                    }, onClick = {
                        context.openLink(link = Link.CODE)
                    }
                )

                CardOfSettings(
                    text = stringResource(PR.string.report_a_bug),
                    icon = {
                        Icon(
                            painter = painterResource(id = DR.drawable.bug_report),
                            contentDescription = "view code",
                            tint = it.suitableColor()
                        )
                    }, onClick = {
                        context.sendEmail(email = Link.EMAIL)
                    }
                )
                // version
                TextForThisTheme(
                    modifier = Modifier
                        .padding(10.dp)
                        .padding(bottom = 20.dp)
                        .align(Alignment.End),
                    fontSize = FontSize.small17,
                    text = "${stringResource(PR.string.version)} ${LocalContext.current.getVersionName()}"
                )
            }
        }
    }
}