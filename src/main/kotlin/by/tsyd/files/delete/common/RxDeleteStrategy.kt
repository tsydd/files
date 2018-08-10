package by.tsyd.files.delete.common

import by.tsyd.files.delete.DeleteStrategy
import by.tsyd.files.fs.api.Directory
import by.tsyd.files.fs.api.File
import by.tsyd.files.fs.api.Path
import by.tsyd.files.fs.kotlin.KotlinFileSystem
import by.tsyd.files.fs.rx.RxFileSystem
import io.reactivex.Observable

class RxDeleteStrategy(
    private val convertFs: (KotlinFileSystem) -> RxFileSystem
) : DeleteStrategy {
    override fun delete(fs: KotlinFileSystem, deleteSelf: Boolean) {
        delete(convertFs(fs), deleteSelf, Path.ROOT)
            .blockingLast(Unit)
    }

    private fun delete(fs: RxFileSystem, deleteSelf: Boolean, path: Path): Observable<Unit> =
        fs.get(path)
            .flatMap { file ->
                when (file) {
                    is File -> fs.delete(path)
                    is Directory -> fs.getChildren(path)
                        .flatMap { delete(fs, true, path + it) }
                        .last(Unit)
                        .toObservable()
                        .flatMap {
                            if (deleteSelf) {
                                fs.delete(path)
                            } else {
                                Observable.empty()
                            }
                        }
                }
            }

}