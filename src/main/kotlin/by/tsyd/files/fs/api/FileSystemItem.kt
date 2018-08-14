package by.tsyd.files.fs.api

/**
 * @author Dmitry Tsydzik
 * @since 6/19/18
 */
typealias FileContent = String?

sealed class FileSystemItem {
    abstract val path: Path

    fun isFile(): Boolean = this is File

    fun isDirectory(): Boolean = this is Directory

    fun asFile(): File = this as File

    fun asDirectory(): Directory = this as Directory
}

data class File(
    override val path: Path,
    val content: FileContent = null
) : FileSystemItem()

data class Directory(override val path: Path) : FileSystemItem()
