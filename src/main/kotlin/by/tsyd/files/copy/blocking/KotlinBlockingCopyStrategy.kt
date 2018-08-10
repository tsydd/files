package by.tsyd.files.copy.blocking

import by.tsyd.files.copy.CopyStrategy
import by.tsyd.files.fs.*
import by.tsyd.files.fs.api.BlockingFileSystem
import by.tsyd.files.fs.api.Directory
import by.tsyd.files.fs.api.File
import by.tsyd.files.fs.api.Path
import by.tsyd.files.fs.kotlin.KotlinFileSystem

/**
 * @author Dmitry Tsydzik
 * @since 7/16/18
 */
class KotlinBlockingCopyStrategy : CopyStrategy {
    override fun copy(sourceFs: KotlinFileSystem, targetFs: KotlinFileSystem) {
        val sourceFsWrapper = SlowBlockingFileSystem(sourceFs)
        val targetFsWrapper = SlowBlockingFileSystem(targetFs)

        copy(sourceFsWrapper, targetFsWrapper, Path.ROOT)
    }

    private fun copy(sourceFs: BlockingFileSystem, targetFs: BlockingFileSystem, path: Path) {
        sourceFs.getChildren(path)
            .map { sourceFs.get(path + it) }
            .forEach {
                when (it) {
                    is File -> targetFs.createFile(it.path, it.content)
                    is Directory -> {
                        targetFs.createDirectory(it.path)
                        copy(sourceFs, targetFs, it.path)
                    }
                }
            }
    }
}