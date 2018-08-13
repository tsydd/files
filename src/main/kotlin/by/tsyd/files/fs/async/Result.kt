package by.tsyd.files.fs.async

import by.tsyd.files.fs.api.FsException

/**
 * @author Dmitry Tsydzik
 * @since 6/19/18
 */
@Suppress("unused")
sealed class Result<T>

data class SuccessResult<T>(
    val data: T
) : Result<T>()

data class ErrorResult<T>(
    val exception: FsException
) : Result<T>()
