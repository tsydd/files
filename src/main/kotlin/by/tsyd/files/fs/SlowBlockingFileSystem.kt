package by.tsyd.files.fs

import by.tsyd.files.fs.api.*
import by.tsyd.files.fs.kotlin.KotlinFileSystem
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.runBlocking

/**
 * @author Dmitry Tsydzik
 * @since 7/14/18
 */
class SlowBlockingFileSystem(
    private val fs: KotlinFileSystem
) : BlockingFileSystem {
    override fun getChildren(path: Path): List<String> = runBlocking(Unconfined) { fs.getChildren(path) }

    override fun get(path: Path): FileSystemItem = runBlocking(Unconfined) { fs.get(path) }

    override fun createFile(path: Path, content: FileContent): File =
        runBlocking(Unconfined) { fs.createFile(path, content) }

    override fun createDirectory(path: Path): Directory = runBlocking(Unconfined) { fs.createDirectory(path) }

    override fun delete(path: Path) = runBlocking { fs.delete(path) }
}
