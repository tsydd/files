package by.tsyd.files.fs.api

/**
 * @author Dmitry Tsydzik
 * @since 7/25/18
 */
interface BlockingFileSystem {
    fun getChildren(path: Path): List<String>

    fun get(path: Path): FileSystemItem

    fun createFile(path: Path, content: FileContent = null): File

    fun createDirectory(path: Path): Directory

    fun delete(path: Path)
}