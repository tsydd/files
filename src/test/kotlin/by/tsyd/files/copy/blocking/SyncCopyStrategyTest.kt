package by.tsyd.files.copy.blocking

import by.tsyd.files.copy.CopyContract
import by.tsyd.files.copy.CopyStrategy
import by.tsyd.files.copy.SyncCopyStrategy

internal class SyncCopyStrategyTest : CopyContract {
    override fun getStrategy(): CopyStrategy = SyncCopyStrategy()
}