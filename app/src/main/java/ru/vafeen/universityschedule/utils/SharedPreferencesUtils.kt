package ru.vafeen.universityschedule.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class SharedPreferences @Inject constructor(
    @ApplicationContext val context: Context
) {
    private val pref = context.getSharedPreferences(
        SharedPreferencesValue.Name.key, Context.MODE_PRIVATE
    )

    fun saveInOrRemoveFromSharedPreferences(save: SharedPreferences.Editor.() -> Unit) {
        pref.edit().apply {
            save()
            apply()
        }
    }

    fun <T> getFromSharedPreferences(get: SharedPreferences.() -> T): T = pref.get()
}

