package by.tsyd.files.copy.blocking.completablefuture

import by.tsyd.files.InstantExecutor
import by.tsyd.files.copy.CopyContract
import by.tsyd.files.copy.CopyStrategy
import by.tsyd.files.copy.common.CompletableFutureCopyStrategy
import by.tsyd.files.fs.SlowBlockingFileSystem
import by.tsyd.files.fs.completablefuture.CompletableFutureBlockingFileSystem

internal class InstantCompletableFutureCopyStrategyTest : CopyContract {

    override fun getStrategy(): CopyStrategy = CompletableFutureCopyStrategy {
        CompletableFutureBlockingFileSystem(SlowBlockingFileSystem(it), InstantExecutor)
    }
}