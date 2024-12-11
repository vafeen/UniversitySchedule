package ru.vafeen.universityschedule.domain

import android.content.Intent

interface MainActivityIntentProvider {
    fun provideIntent(): Intent
}