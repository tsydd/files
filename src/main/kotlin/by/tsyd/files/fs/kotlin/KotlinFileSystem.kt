package by.tsyd.files.fs.kotlin

import by.tsyd.files.fs.api.*

/**
 * @author Dmitry Tsydzik
 * @since 7/25/18
 */
interface KotlinFileSystem {
    suspend fun getChildren(path: Path): List<String>

    suspend fun get(path: Path): FileSystemItem

    suspend fun createFile(path: Path, content: FileContent = null): File

    suspend fun createDirectory(path: Path): Directory

    suspend fun delete(path: Path)
}