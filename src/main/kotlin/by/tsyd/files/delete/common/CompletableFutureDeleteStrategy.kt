package by.tsyd.files.delete.common

import by.tsyd.files.delete.DeleteStrategy
import by.tsyd.files.fs.api.Directory
import by.tsyd.files.fs.api.File
import by.tsyd.files.fs.api.Path
import by.tsyd.files.fs.completablefuture.CompletableFutureFileSystem
import by.tsyd.files.fs.kotlin.KotlinFileSystem
import java.util.concurrent.CompletableFuture

class CompletableFutureDeleteStrategy(
    private val convertFs: (KotlinFileSystem) -> CompletableFutureFileSystem
) : DeleteStrategy {
    override fun delete(fs: KotlinFileSystem, deleteSelf: Boolean) {
        delete(convertFs(fs), deleteSelf, Path.ROOT)
            .get()
    }

    private fun delete(fs: CompletableFutureFileSystem, deleteSelf: Boolean, path: Path): CompletableFuture<Unit> {
        return fs.get(path)
            .thenCompose { file ->
                when (file) {
                    is File -> fs.delete(path)
                    is Directory -> fs.getChildren(path)
                        .thenCompose { childNames ->
                            val deleteChildFutures = childNames.map { delete(fs, true, path + it) }
                            CompletableFuture.allOf(*deleteChildFutures.toTypedArray())
                                .thenCompose {
                                    if (deleteSelf) {
                                        fs.delete(path)
                                    } else {
                                        CompletableFuture.completedFuture(Unit)
                                    }
                                }
                        }
                }
            }
    }
}