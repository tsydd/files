package by.tsyd.files.delete

import by.tsyd.files.fs.kotlin.KotlinFileSystem

interface DeleteStrategy {
    fun delete(fs: KotlinFileSystem, deleteSelf: Boolean = false)
}