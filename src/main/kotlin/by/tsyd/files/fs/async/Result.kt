package by.tsyd.files.fs.async

import by.tsyd.files.fs.api.FsException

/**
 * @author Dmitry Tsydzik
 * @since 6/19/18
 */
class Result<T> private constructor(
    val data: T? = null,
    val exception: FsException? = null,
    val ok: Boolean
) {
    companion object {
        fun <T> ofError(exception: FsException) = Result<T>(exception = exception, ok = false)

        fun <T> of(data: T) = Result(data = data, ok = true)
    }
}