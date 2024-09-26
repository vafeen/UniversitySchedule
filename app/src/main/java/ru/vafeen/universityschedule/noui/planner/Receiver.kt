package ru.vafeen.universityschedule.noui.planner

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        Toast.makeText(context, "Будильник сработал!", Toast.LENGTH_SHORT).show()
    }
}