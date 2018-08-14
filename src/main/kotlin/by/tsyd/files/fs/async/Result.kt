package by.tsyd.files.fs.async

import by.tsyd.files.fs.api.FsException

/**
 * @author Dmitry Tsydzik
 * @since 6/19/18
 */
sealed class Result<T> {
    fun isOk(): Boolean = this is SuccessResult

    fun asSuccess(): SuccessResult<T> = this as SuccessResult<T>

    fun asError(): ErrorResult<T> = this as ErrorResult<T>
}

data class SuccessResult<T>(
    val data: T
) : Result<T>()

data class ErrorResult<T>(
    val exception: FsException
) : Result<T>()
