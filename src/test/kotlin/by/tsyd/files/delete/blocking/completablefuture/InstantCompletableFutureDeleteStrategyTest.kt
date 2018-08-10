package by.tsyd.files.delete.blocking.completablefuture

import by.tsyd.files.InstantExecutor
import by.tsyd.files.delete.DeleteContract
import by.tsyd.files.delete.DeleteStrategy
import by.tsyd.files.delete.common.CompletableFutureDeleteStrategy
import by.tsyd.files.fs.SlowBlockingFileSystem
import by.tsyd.files.fs.completablefuture.CompletableFutureBlockingFileSystem

/**
 * @author Dmitry Tsydzik
 * @since 8/14/18
 */
class InstantCompletableFutureDeleteStrategyTest : DeleteContract {
    override fun getStrategy(): DeleteStrategy = CompletableFutureDeleteStrategy {
        CompletableFutureBlockingFileSystem(SlowBlockingFileSystem(it), InstantExecutor)
    }
}