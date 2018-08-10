package by.tsyd.files.fs.completablefuture

import by.tsyd.files.fs.api.*
import by.tsyd.files.fs.async.AsyncFileSystem
import by.tsyd.files.fs.async.ErrorResult
import by.tsyd.files.fs.async.Result
import by.tsyd.files.fs.async.SuccessResult
import java.util.concurrent.CompletableFuture

class CompletableFutureAsyncFileSystem(
    private val asyncFs: AsyncFileSystem
) : CompletableFutureFileSystem {
    override fun get(path: Path): CompletableFuture<FileSystemItem> = handle {
        asyncFs.get(path, it)
    }

    override fun getChildren(path: Path): CompletableFuture<List<String>> = handle {
        asyncFs.getChildren(path, it)
    }

    override fun createFile(path: Path, content: FileContent): CompletableFuture<File> = handle {
        asyncFs.createFile(path, content, it)
    }

    override fun createDirectory(path: Path): CompletableFuture<Directory> = handle {
        asyncFs.createDirectory(path, it)
    }

    override fun delete(path: Path): CompletableFuture<Unit> = handle {
        asyncFs.delete(path, it)
    }

    private inline fun <T> handle(block: ((Result<T>) -> Unit) -> Unit): CompletableFuture<T> {
        val result = CompletableFuture<T>()
        block {
            when (it) {
                is SuccessResult -> result.complete(it.data)
                is ErrorResult -> result.completeExceptionally(it.exception)
            }
        }
        return result
    }
}