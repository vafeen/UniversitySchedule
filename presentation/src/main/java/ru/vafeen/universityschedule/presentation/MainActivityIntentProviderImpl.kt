package ru.vafeen.universityschedule.presentation

import android.content.Context
import android.content.Intent
import ru.vafeen.universityschedule.domain.MainActivityIntentProvider

class MainActivityIntentProviderImpl(private val context: Context) : MainActivityIntentProvider {
    override fun provideIntent(): Intent = Intent(context, MainActivity::class.java)
}