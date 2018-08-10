package by.tsyd.files.delete.blocking

import by.tsyd.files.delete.DeleteContract
import by.tsyd.files.delete.DeleteStrategy
import by.tsyd.files.delete.SyncDeleteStrategy

class SyncDeleteStrategyTest : DeleteContract {
    override fun getStrategy(): DeleteStrategy = SyncDeleteStrategy()
}