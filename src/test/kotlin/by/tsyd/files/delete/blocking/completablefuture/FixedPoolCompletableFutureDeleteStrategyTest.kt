package by.tsyd.files.delete.blocking.completablefuture

import by.tsyd.files.delete.DeleteContract
import by.tsyd.files.delete.DeleteStrategy
import by.tsyd.files.delete.common.CompletableFutureDeleteStrategy
import by.tsyd.files.fs.SlowBlockingFileSystem
import by.tsyd.files.fs.completablefuture.CompletableFutureBlockingFileSystem
import java.util.concurrent.Executors

/**
 * @author Dmitry Tsydzik
 * @since 8/14/18
 */
class FixedPoolCompletableFutureDeleteStrategyTest : DeleteContract {
    companion object {
        const val POOL_SIZE = 5
    }

    override fun getStrategy(): DeleteStrategy = CompletableFutureDeleteStrategy {
        CompletableFutureBlockingFileSystem(SlowBlockingFileSystem(it), Executors.newFixedThreadPool(POOL_SIZE))
    }
}