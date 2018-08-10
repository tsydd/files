package by.tsyd.files.delete.blocking.rx

import by.tsyd.files.delete.DeleteContract
import by.tsyd.files.delete.DeleteStrategy
import by.tsyd.files.delete.common.RxDeleteStrategy
import by.tsyd.files.fs.SlowBlockingFileSystem
import by.tsyd.files.fs.rx.RxBlockingFileSystem
import io.reactivex.schedulers.Schedulers

/**
 * @author Dmitry Tsydzik
 * @since 8/14/18
 */
class TrampolineRxDeleteStrategyTest : DeleteContract {
    override fun getStrategy(): DeleteStrategy = RxDeleteStrategy {
        RxBlockingFileSystem(SlowBlockingFileSystem(it), Schedulers.trampoline())
    }
}