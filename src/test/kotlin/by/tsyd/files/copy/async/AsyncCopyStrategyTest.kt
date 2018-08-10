package by.tsyd.files.copy.async

import by.tsyd.files.copy.AsyncCopyContract
import by.tsyd.files.copy.CopyContract
import by.tsyd.files.copy.CopyStrategy

internal class AsyncCopyStrategyTest : CopyContract, AsyncCopyContract {
    override fun getStrategy(): CopyStrategy = AsyncCopyStrategy()
}