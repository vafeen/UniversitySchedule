package ru.vafeen.universityschedule.presentation.utils

import ru.vafeen.universityschedule.domain.GSheetsServiceRequestStatus
import ru.vafeen.universityschedule.resources.R


internal fun getIconByRequestStatus(networkState: GSheetsServiceRequestStatus): Int =
    when (networkState) {
        GSheetsServiceRequestStatus.Waiting -> {
            R.drawable.sync
        }

        GSheetsServiceRequestStatus.Success -> {
            R.drawable.updated
        }

        GSheetsServiceRequestStatus.NetworkError -> {
            R.drawable.no_wifi
        }

        GSheetsServiceRequestStatus.NoLink -> {
            R.drawable.no_link
        }
    }