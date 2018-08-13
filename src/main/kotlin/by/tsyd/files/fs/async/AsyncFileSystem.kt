package by.tsyd.files.fs.async

import by.tsyd.files.fs.api.*

/**
 * @author Dmitry Tsydzik
 * @since 7/25/18
 */
interface AsyncFileSystem {
    fun getChildren(path: Path, onResult: (Result<List<String>>) -> Unit)

    fun get(path: Path, onResult: (Result<FileSystemItem>) -> Unit)

    fun createFile(path: Path, content: FileContent, onResult: (Result<File>) -> Unit)

    fun createDirectory(path: Path, onResult: (Result<Directory>) -> Unit)

    fun delete(path: Path, onResult: (Result<Unit>) -> Unit)
}