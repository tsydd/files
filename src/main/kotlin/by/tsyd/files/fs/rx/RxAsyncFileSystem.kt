package by.tsyd.files.fs.rx

import by.tsyd.files.fs.api.*
import by.tsyd.files.fs.async.AsyncFileSystem
import by.tsyd.files.fs.async.ErrorResult
import by.tsyd.files.fs.async.Result
import by.tsyd.files.fs.async.SuccessResult
import io.reactivex.Observable

class RxAsyncFileSystem(
    private val fs: AsyncFileSystem
) : RxFileSystem {
    override fun get(path: Path): Observable<FileSystemItem> = handleRequest {
        fs.get(path, it)
    }

    override fun getChildren(path: Path): Observable<String> =
        handleRequest<List<String>> {
            fs.getChildren(path, it)
        }.flatMap { Observable.fromIterable(it) }

    override fun createFile(path: Path, content: FileContent): Observable<File> = handleRequest {
        fs.createFile(path, content, it)
    }

    override fun createDirectory(path: Path): Observable<Directory> = handleRequest {
        fs.createDirectory(path, it)
    }

    override fun delete(path: Path): Observable<Unit> = handleRequest {
        fs.delete(path, it)
    }

    private fun <T> handleRequest(block: ((Result<T>) -> Unit) -> Unit): Observable<T> = Observable.create { emitter ->
        block {
            when (it) {
                is SuccessResult -> {
                    emitter.onNext(it.data)
                    emitter.onComplete()
                }
                is ErrorResult -> emitter.onError(it.exception)
            }
        }
    }
}