package by.tsyd.files.copy

import by.tsyd.files.fs.kotlin.KotlinFileSystem

/**
 * @author Dmitry Tsydzik
 * @since 7/16/18
 */
interface CopyStrategy {
    fun copy(sourceFs: KotlinFileSystem, targetFs: KotlinFileSystem)
}