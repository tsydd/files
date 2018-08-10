package by.tsyd.files.copy.async

import by.tsyd.files.copy.AsyncCopyContract
import by.tsyd.files.copy.CopyContract
import by.tsyd.files.copy.CopyStrategy
import by.tsyd.files.copy.common.CompletableFutureCopyStrategy
import by.tsyd.files.fs.async.AsyncFileSystemImpl
import by.tsyd.files.fs.completablefuture.CompletableFutureAsyncFileSystem

internal class AsyncCompletableFutureCopyStrategyTest : CopyContract, AsyncCopyContract {
    override fun getStrategy(): CopyStrategy = CompletableFutureCopyStrategy {
        CompletableFutureAsyncFileSystem(AsyncFileSystemImpl(it))
    }
}