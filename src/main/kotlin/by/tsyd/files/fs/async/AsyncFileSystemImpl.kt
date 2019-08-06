package by.tsyd.files.fs.async

import by.tsyd.files.fs.api.*
import by.tsyd.files.fs.kotlin.KotlinFileSystem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @author Dmitry Tsydzik
 * @since 6/19/18
 */
class AsyncFileSystemImpl(
    private val fs: KotlinFileSystem
) : AsyncFileSystem {

    override fun getChildren(path: Path, onResult: (Result<List<String>>) -> Unit) =
        handleResult(onResult) {
            fs.getChildren(path)
        }

    override fun get(path: Path, onResult: (Result<FileSystemItem>) -> Unit) =
        handleResult(onResult) {
            fs.get(path)
        }

    override fun createFile(path: Path, content: FileContent, onResult: (Result<File>) -> Unit) =
        handleResult(onResult) {
            fs.createFile(path, content)
        }

    override fun createDirectory(path: Path, onResult: (Result<Directory>) -> Unit) =
        handleResult(onResult) {
            fs.createDirectory(path)
        }

    override fun delete(path: Path, onResult: (Result<Unit>) -> Unit) =
        handleResult(onResult) {
            fs.delete(path)
        }

    private fun <T> handleResult(onResult: (Result<T>) -> Unit, block: suspend () -> T) {
        GlobalScope.launch(Dispatchers.Unconfined) {
            val result: T = try {
                block()
            } catch (e: FsException) {
                onResult(ErrorResult(e))
                return@launch
            }
            onResult(SuccessResult(result))
        }
    }
}
