package by.tsyd.files.delete.blocking.completablefuture

import by.tsyd.files.delete.DeleteContract
import by.tsyd.files.delete.DeleteStrategy
import by.tsyd.files.delete.common.CompletableFutureDeleteStrategy
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
class CachedPoolCompletableFutureDeleteStrategyTest : DeleteContract {
    private val log = getLogger(javaClass)

    private lateinit var executorService: ThreadPoolExecutor

    override fun getStrategy(): DeleteStrategy = CompletableFutureDeleteStrategy {
        CompletableFutureBlockingFileSystem(SlowBlockingFileSystem(it), executorService)
    }

    @BeforeEach
    internal fun setUp() {
        executorService = Executors.newCachedThreadPool() as ThreadPoolExecutor
    }

    @AfterEach
    internal fun tearDown() {
        log.info("Pool size = {}", executorService.poolSize)
    }
}