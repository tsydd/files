package by.tsyd.files.fs.kotlin

import by.tsyd.files.fs.BlockingFileSystemImpl
import by.tsyd.files.fs.api.*
import by.tsyd.files.getLogger
import kotlinx.coroutines.experimental.delay

/**
 * @author Dmitry Tsydzik
 * @since 7/14/18
 */
class KotlinDelayFileSystem(
    private val delayMilliseconds: Long,
    private val internalFs: BlockingFileSystem = BlockingFileSystemImpl()
) : KotlinFileSystem {
    private val log = getLogger(javaClass)

    override suspend fun getChildren(path: Path): List<String> {
        log.trace("Requesting children for {}", path)
        delay(delayMilliseconds)
        return internalFs.getChildren(path)
    }

    override suspend fun delete(path: Path) {
        log.trace("Requesting delete {}", path)
        delay(delayMilliseconds)
        internalFs.delete(path)
    }

    override suspend fun get(path: Path): FileSystemItem {
        log.trace("Requesting {}", path)
        delay(delayMilliseconds)
        return internalFs.get(path)
    }

    override suspend fun createFile(path: Path, content: FileContent): File {
        log.trace("Requesting creating file {}", path)
        delay(delayMilliseconds)
        return internalFs.createFile(path, content)
    }

    override suspend fun createDirectory(path: Path): Directory {
        log.trace("Requesting creating directory {}", path)
        delay(delayMilliseconds)
        return internalFs.createDirectory(path)
    }

    override fun toString() = internalFs.toString()

    override fun equals(other: Any?): Boolean = when (other) {
        is KotlinDelayFileSystem -> internalFs == other.internalFs
        else -> false
    }

    override fun hashCode(): Int = internalFs.hashCode()
}