package by.tsyd.files.delete.blocking.rx

import by.tsyd.files.delete.DeleteContract
import by.tsyd.files.delete.DeleteStrategy
import by.tsyd.files.delete.common.RxDeleteStrategy
import by.tsyd.files.fs.SlowBlockingFileSystem
import by.tsyd.files.fs.rx.RxBlockingFileSystem
import by.tsyd.files.getLogger
import io.reactivex.internal.schedulers.IoScheduler
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

/**
 * @author Dmitry Tsydzik
 * @since 8/14/18
 */
class IoRxDeleteStrategyTest : DeleteContract {
    private val log = getLogger(javaClass)

    private lateinit var scheduler: IoScheduler

    override fun getStrategy(): DeleteStrategy = RxDeleteStrategy {
        RxBlockingFileSystem(SlowBlockingFileSystem(it), scheduler)
    }

    @BeforeEach
    internal fun setUp() {
        scheduler = IoScheduler()
    }

    @AfterEach
    internal fun tearDown() {
        log.info("Pool size = {}", scheduler.size())
    }

}