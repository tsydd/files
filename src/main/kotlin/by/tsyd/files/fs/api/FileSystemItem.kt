package by.tsyd.files.fs.api

/**
 * @author Dmitry Tsydzik
 * @since 6/19/18
 */
typealias FileContent = String?

sealed class FileSystemItem {
    abstract val path: Path
}

data class File(
    override val path: Path,
    val content: FileContent = null
) : FileSystemItem()

data class Directory(override val path: Path) : FileSystemItem()
