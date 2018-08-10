package by.tsyd.files.delete.async

import by.tsyd.files.delete.AsyncDeleteContract
import by.tsyd.files.delete.DeleteContract
import by.tsyd.files.delete.DeleteStrategy
import by.tsyd.files.delete.common.CompletableFutureDeleteStrategy
import by.tsyd.files.fs.async.AsyncFileSystemImpl
import by.tsyd.files.fs.completablefuture.CompletableFutureAsyncFileSystem

internal class AsyncCompletableFutureDeleteStrategyTest : DeleteContract, AsyncDeleteContract {
    override fun getStrategy(): DeleteStrategy = CompletableFutureDeleteStrategy {
        CompletableFutureAsyncFileSystem(AsyncFileSystemImpl(it))
    }
}