package by.tsyd.files.fs.rx

import by.tsyd.files.fs.api.*
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Scheduler

/**
 * @author Dmitry Tsydzik
 * @since 7/16/18
 */
class RxBlockingFileSystem(
    private val fs: BlockingFileSystem,
    private val scheduler: Scheduler
) : RxFileSystem {

    override fun get(path: Path): Observable<FileSystemItem> = handleResult { fs.get(path) }

    override fun getChildren(path: Path): Observable<String> = handleResult { fs.getChildren(path) }
        .flatMap { Observable.fromIterable(it) }

    override fun createFile(path: Path, content: FileContent): Observable<File> =
        handleResult { fs.createFile(path, content) }

    override fun createDirectory(path: Path): Observable<Directory> = handleResult { fs.createDirectory(path) }

    override fun delete(path: Path): Observable<Unit> = handleResult { fs.delete(path) }

    private fun <T> handleResult(block: () -> T): Observable<T> =
        Observable.create { subscriber: ObservableEmitter<in T> ->
            val result = try {
                block()
            } catch (e: FsException) {
                subscriber.onError(e)
                return@create
            }
            subscriber.onNext(result)
            subscriber.onComplete()
        }.subscribeOn(scheduler)

}