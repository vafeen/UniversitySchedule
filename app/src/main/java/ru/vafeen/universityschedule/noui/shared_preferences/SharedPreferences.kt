package ru.vafeen.universityschedule.noui.shared_preferences

import android.content.Context
import android.content.SharedPreferences


class SharedPreferences(
    private val context: Context
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

