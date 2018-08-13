package by.tsyd.files.fs.completablefuture

import by.tsyd.files.fs.api.*
import java.util.concurrent.CompletableFuture

/**
 * @author Dmitry Tsydzik
 * @since 7/25/18
 */
interface CompletableFutureFileSystem {
    fun get(path: Path): CompletableFuture<FileSystemItem>

    fun getChildren(path: Path): CompletableFuture<List<String>>

    fun createFile(path: Path, content: FileContent = null): CompletableFuture<File>

    fun createDirectory(path: Path): CompletableFuture<Directory>

    fun delete(path: Path): CompletableFuture<Unit>
}