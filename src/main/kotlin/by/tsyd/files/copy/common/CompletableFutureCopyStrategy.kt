package by.tsyd.files.copy.common

import by.tsyd.files.copy.CopyStrategy
import by.tsyd.files.fs.api.Directory
import by.tsyd.files.fs.api.File
import by.tsyd.files.fs.api.FileSystemItem
import by.tsyd.files.fs.api.Path
import by.tsyd.files.fs.completablefuture.CompletableFutureFileSystem
import by.tsyd.files.fs.kotlin.KotlinFileSystem
import java.util.concurrent.CompletableFuture

/**
 * @author Dmitry Tsydzik
 * @since 7/25/18
 */
class CompletableFutureCopyStrategy(
    private val convertFs: (KotlinFileSystem) -> CompletableFutureFileSystem
) : CopyStrategy {
    override fun copy(sourceFs: KotlinFileSystem, targetFs: KotlinFileSystem) {
        val sourceFsWrapper = convertFs(sourceFs)
        val targetFsWrapper = convertFs(targetFs)

        copy(sourceFsWrapper, targetFsWrapper, Path.ROOT)
            .join()
    }

    private fun copy(
        sourceFs: CompletableFutureFileSystem,
        targetFs: CompletableFutureFileSystem,
        path: Path
    ): CompletableFuture<out Any?> =
        sourceFs.getChildren(path)
            .thenCompose { names ->
                val futures = names.asSequence()
                    .map { sourceFs.get(path + it) }
                    .map { copy(sourceFs, targetFs, it) }
                    .toList()
                CompletableFuture.allOf(*futures.toTypedArray())
            }

    private fun copy(
        sourceFs: CompletableFutureFileSystem,
        targetFs: CompletableFutureFileSystem,
        item: CompletableFuture<FileSystemItem>
    ): CompletableFuture<out Any?> = item.thenCompose { child ->
        when (child) {
            is File -> targetFs.createFile(child.path, child.content).thenApply { null }
            is Directory -> targetFs
                .createDirectory(child.path)
                .thenCompose { directory ->
                    copy(sourceFs, targetFs, directory.path).thenApply { null }
                }
        }
    }

}