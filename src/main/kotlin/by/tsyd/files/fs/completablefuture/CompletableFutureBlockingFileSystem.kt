package by.tsyd.files.fs.completablefuture

import by.tsyd.files.fs.api.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor
import java.util.function.Supplier

/**
 * @author Dmitry Tsydzik
 * @since 7/25/18
 */
class CompletableFutureBlockingFileSystem(
    private val fs: BlockingFileSystem,
    private val executor: Executor
) : CompletableFutureFileSystem {

    override fun get(path: Path): CompletableFuture<FileSystemItem> =
        CompletableFuture.supplyAsync(Supplier { fs.get(path) }, executor)

    override fun getChildren(path: Path): CompletableFuture<List<String>> =
        CompletableFuture.supplyAsync(Supplier { fs.getChildren(path) }, executor)

    override fun createFile(path: Path, content: FileContent): CompletableFuture<File> =
        CompletableFuture.supplyAsync(Supplier { fs.createFile(path, content) }, executor)

    override fun createDirectory(path: Path): CompletableFuture<Directory> =
        CompletableFuture.supplyAsync(Supplier { fs.createDirectory(path) }, executor)

    override fun delete(path: Path): CompletableFuture<Unit> =
        CompletableFuture.supplyAsync(Supplier { fs.delete(path) }, executor)
}