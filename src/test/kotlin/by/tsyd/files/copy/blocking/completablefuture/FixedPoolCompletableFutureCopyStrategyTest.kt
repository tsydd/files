package by.tsyd.files.copy.blocking.completablefuture

import by.tsyd.files.copy.CopyContract
import by.tsyd.files.copy.CopyStrategy
import by.tsyd.files.copy.common.CompletableFutureCopyStrategy
import by.tsyd.files.fs.SlowBlockingFileSystem
import by.tsyd.files.fs.completablefuture.CompletableFutureBlockingFileSystem
import java.util.concurrent.Executors

internal class FixedPoolCompletableFutureCopyStrategyTest : CopyContract {
    companion object {
        private const val THREAD_COUNT = 5
    }

    override fun getStrategy(): CopyStrategy = CompletableFutureCopyStrategy {
        CompletableFutureBlockingFileSystem(SlowBlockingFileSystem(it), Executors.newFixedThreadPool(THREAD_COUNT))
    }
}