package by.tsyd.files.delete.async

import by.tsyd.files.delete.AsyncDeleteContract
import by.tsyd.files.delete.DeleteContract
import by.tsyd.files.delete.DeleteStrategy
import by.tsyd.files.delete.common.RxDeleteStrategy
import by.tsyd.files.fs.async.AsyncFileSystemImpl
import by.tsyd.files.fs.rx.RxAsyncFileSystem

class AsyncRxDeleteStrategyTest : DeleteContract, AsyncDeleteContract {
    override fun getStrategy(): DeleteStrategy = RxDeleteStrategy { RxAsyncFileSystem(AsyncFileSystemImpl(it)) }
}