package ru.vafeen.universityschedule.domain.network.result_status.sheet_data

class ErrorSheetDataResult<T>(
    val exception: Exception
) : SheetDataResult<T>()
