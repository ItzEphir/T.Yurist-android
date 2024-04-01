package ru.sber_tech.data.addMeetScreen

sealed interface ErrorState {
    data object DateError: ErrorState
    data object TimeError: ErrorState
    data object EventError: ErrorState
    data object HttpError: ErrorState
    data object TimeoutError: ErrorState
    data object ReceiptError: ErrorState
}
