package by.tsyd.files.copy.async

import by.tsyd.files.copy.CopyStrategy
import by.tsyd.files.fs.api.Directory
import by.tsyd.files.fs.api.File
import by.tsyd.files.fs.api.Path
import by.tsyd.files.fs.async.AsyncFileSystem
import by.tsyd.files.fs.async.AsyncFileSystemImpl
import by.tsyd.files.fs.async.ErrorResult
import by.tsyd.files.fs.async.SuccessResult
import by.tsyd.files.fs.kotlin.KotlinFileSystem
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicInteger

class AsyncCopyStrategy : CopyStrategy {
    override fun copy(sourceFs: KotlinFileSystem, targetFs: KotlinFileSystem) {
        val sourceFsWrapper = AsyncFileSystemImpl(sourceFs)
        val targetFsWrapper = AsyncFileSystemImpl(targetFs)
        val latch = CountDownLatch(1)
        copy(sourceFsWrapper, targetFsWrapper, Path.ROOT) {
            latch.countDown()
        }
        latch.await()
    }

    private fun copy(sourceFs: AsyncFileSystem, targetFs: AsyncFileSystem, path: Path, onResult: () -> Unit) {
        sourceFs.getChildren(path) { getChildrenResult ->
            when (getChildrenResult) {
                is ErrorResult -> onResult()
                is SuccessResult -> {
                    val fileNames = getChildrenResult.data
                    if (fileNames.isEmpty()) {
                        onResult()
                    } else {
                        val counter = AtomicInteger(fileNames.size)
                        val callback = {
                            if (counter.decrementAndGet() == 0) {
                                onResult()
                            }
                        }
                        fileNames.forEach { childName ->
                            sourceFs.get(path + childName) { getFileResult ->
                                when (getFileResult) {
                                    is ErrorResult -> callback()
                                    is SuccessResult -> {
                                        val file = getFileResult.data
                                        when (file) {
                                            is File -> targetFs.createFile(file.path, file.content) {
                                                callback()
                                            }
                                            is Directory -> targetFs.createDirectory(file.path) { createDirectoryResult ->
                                                when (createDirectoryResult) {
                                                    is ErrorResult -> callback()
                                                    is SuccessResult -> copy(sourceFs, targetFs, file.path, callback)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}