package by.tsyd.files.copy.blocking.rx

import by.tsyd.files.copy.CopyContract
import by.tsyd.files.copy.CopyStrategy
import by.tsyd.files.copy.common.RxCopyStrategy
import by.tsyd.files.fs.SlowBlockingFileSystem
import by.tsyd.files.fs.rx.RxBlockingFileSystem
import by.tsyd.files.getLogger
import io.reactivex.internal.schedulers.IoScheduler
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

internal class IoRxCopyStrategyTest : CopyContract {

    private val log = getLogger(javaClass)

    private lateinit var scheduler: IoScheduler

    override fun getStrategy(): CopyStrategy = RxCopyStrategy {
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