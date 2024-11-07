package ru.vafeen.universityschedule.domain.network.result_status.sheet_data

sealed class SheetDataResult<T>

class PendingSheetDataResult<T> : SheetDataResult<T>()
class ErrorSheetDataResult<T>(
    val exception: Exception
) : SheetDataResult<T>()

class SuccessSheetDataResult<T>(
    val data: T
) : SheetDataResult<T>()
