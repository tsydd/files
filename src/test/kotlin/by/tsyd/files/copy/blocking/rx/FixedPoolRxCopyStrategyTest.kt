package by.tsyd.files.copy.blocking.rx

import by.tsyd.files.copy.CopyContract
import by.tsyd.files.copy.CopyStrategy
import by.tsyd.files.copy.common.RxCopyStrategy
import by.tsyd.files.fs.SlowBlockingFileSystem
import by.tsyd.files.fs.rx.RxBlockingFileSystem
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

internal class FixedPoolRxCopyStrategyTest : CopyContract {
    companion object {
        private const val THREAD_COUNT = 40
    }

    override fun getStrategy(): CopyStrategy = RxCopyStrategy {
        RxBlockingFileSystem(SlowBlockingFileSystem(it), Schedulers.from(Executors.newFixedThreadPool(THREAD_COUNT)))
    }
}