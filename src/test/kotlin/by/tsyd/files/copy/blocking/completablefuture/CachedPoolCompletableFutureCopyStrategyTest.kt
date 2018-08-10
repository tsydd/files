package by.tsyd.files.copy.blocking.completablefuture

import by.tsyd.files.copy.CopyContract
import by.tsyd.files.copy.CopyStrategy
import by.tsyd.files.copy.common.CompletableFutureCopyStrategy
import by.tsyd.files.fs.SlowBlockingFileSystem
import by.tsyd.files.fs.completablefuture.CompletableFutureBlockingFileSystem
import by.tsyd.files.getLogger
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor

/**
 * @author Dmitry Tsydzik
 * @since 8/14/18
 */
class CachedPoolCompletableFutureCopyStrategyTest : CopyContract {

    private val log = getLogger(javaClass)

    private lateinit var executor: ThreadPoolExecutor

    override fun getStrategy(): CopyStrategy = CompletableFutureCopyStrategy {
        CompletableFutureBlockingFileSystem(SlowBlockingFileSystem(it), executor)
    }

    @BeforeEach
    internal fun setUp() {
        executor = Executors.newCachedThreadPool() as ThreadPoolExecutor
    }

    @AfterEach
    internal fun tearDown() {
        log.info("Pool size = {}", executor.poolSize)
    }
}