package ru.vafeen.universityschedule.ui.components.viewModels

import android.content.Context
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.vafeen.universityschedule.R
import ru.vafeen.universityschedule.utils.SharedPreferencesValue
import javax.inject.Inject

class SettingsScreenViewModel @Inject constructor(@ApplicationContext context: Context) :
    ViewModel() {
    val spaceBetweenCards = 30.dp
    val noLink = context.getString(R.string.no_link)
    private val pref = context.getSharedPreferences(
        SharedPreferencesValue.Name.key, Context.MODE_PRIVATE
    )
    val getLinkCallBack = { pref.getString(SharedPreferencesValue.Link.key, noLink) ?: noLink }

}