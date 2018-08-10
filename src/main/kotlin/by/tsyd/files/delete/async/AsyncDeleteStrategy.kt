package by.tsyd.files.delete.async

import by.tsyd.files.delete.DeleteStrategy
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

class AsyncDeleteStrategy : DeleteStrategy {
    override fun delete(fs: KotlinFileSystem, deleteSelf: Boolean) {
        val latch = CountDownLatch(1)
        delete(AsyncFileSystemImpl(fs), Path.ROOT, false) {
            latch.countDown()
        }
        latch.await()
    }

    private fun delete(fs: AsyncFileSystem, path: Path, deleteSelf: Boolean, callback: () -> Unit) {
        fun doDeleteSelf() {
            if (deleteSelf) {
                fs.delete(path) { callback() }
            } else {
                callback()
            }
        }

        fs.get(path) { getFileResult ->
            when (getFileResult) {
                is ErrorResult -> callback()
                is SuccessResult -> {
                    when (getFileResult.data) {
                        is File -> doDeleteSelf()
                        is Directory -> fs.getChildren(path) { getChildrenResult ->
                            when (getChildrenResult) {
                                is ErrorResult -> callback()
                                is SuccessResult -> {
                                    if (getChildrenResult.data.isEmpty()) {
                                        doDeleteSelf()
                                    } else {
                                        val counter = AtomicInteger(getChildrenResult.data.size)
                                        val onDeleteChildCallback = {
                                            if (counter.decrementAndGet() == 0) {
                                                doDeleteSelf()
                                            }
                                        }
                                        getChildrenResult.data.forEach { childName ->
                                            delete(fs, path + childName, true, onDeleteChildCallback)
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