package by.tsyd.files.copy.common

import by.tsyd.files.copy.CopyStrategy
import by.tsyd.files.fs.api.Directory
import by.tsyd.files.fs.api.File
import by.tsyd.files.fs.api.Path
import by.tsyd.files.fs.kotlin.KotlinFileSystem
import by.tsyd.files.fs.rx.RxFileSystem
import io.reactivex.Observable


/**
 * @author Dmitry Tsydzik
 * @since 7/16/18
 */
class RxCopyStrategy(
    private val convertFs: (KotlinFileSystem) -> RxFileSystem
) : CopyStrategy {

    override fun copy(sourceFs: KotlinFileSystem, targetFs: KotlinFileSystem) {
        val sourceFsWrapper = convertFs(sourceFs)
        val targetFsWrapper = convertFs(targetFs)
        copy(sourceFsWrapper, targetFsWrapper, Path.ROOT)
            .blockingLast(null)
    }

    private fun copy(sourceFs: RxFileSystem, targetFs: RxFileSystem, path: Path): Observable<out Any?> =
        sourceFs.getChildren(path)
            .flatMap { sourceFs.get(path + it) }
            .flatMap { file ->
                when (file) {
                    is File -> targetFs.createFile(file.path, file.content)
                    is Directory -> targetFs.createDirectory(file.path)
                        .flatMap { copy(sourceFs, targetFs, it.path) }
                }
            }

}