package ru.vafeen.universityschedule.domain.shared_preferences

import android.content.SharedPreferences


fun SharedPreferences.saveInOrRemoveFromSharedPreferences(save: SharedPreferences.Editor.() -> Unit) {
    edit().apply {
        save()
        apply()
    }
}

fun <T> SharedPreferences.getFromSharedPreferences(get: SharedPreferences.() -> T): T = get()


