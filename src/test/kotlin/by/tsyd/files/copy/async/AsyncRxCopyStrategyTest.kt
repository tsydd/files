package by.tsyd.files.copy.async

import by.tsyd.files.copy.AsyncCopyContract
import by.tsyd.files.copy.CopyContract
import by.tsyd.files.copy.CopyStrategy
import by.tsyd.files.copy.common.RxCopyStrategy
import by.tsyd.files.fs.async.AsyncFileSystemImpl
import by.tsyd.files.fs.rx.RxAsyncFileSystem

internal class AsyncRxCopyStrategyTest : CopyContract, AsyncCopyContract {
    override fun getStrategy(): CopyStrategy = RxCopyStrategy {
        RxAsyncFileSystem(AsyncFileSystemImpl(it))
    }
}