package com.smartcam.ai.core

sealed interface AppResult<out T> {
    data class Success<T>(val data: T) : AppResult<T>
    data class Failure(val error: AppError, val cause: Throwable? = null) : AppResult<Nothing>
}

sealed interface AppError {
    data object NetworkUnavailable : AppError
    data object CameraUnavailable : AppError
    data object StorageUnavailable : AppError
    data object DatabaseFailure : AppError
    data class Unknown(val message: String) : AppError
}
