package ru.vafeen.universityschedule.domain.network.result_status.sheet_data


class SuccessSheetDataResult<T>(
    val data: T
) : SheetDataResult<T>()
