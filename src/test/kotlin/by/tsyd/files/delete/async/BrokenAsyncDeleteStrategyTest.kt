package by.tsyd.files.delete.async

import by.tsyd.files.delete.AsyncDeleteContract
import by.tsyd.files.delete.BrokenAsyncDeleteStrategy
import by.tsyd.files.delete.DeleteContract
import by.tsyd.files.delete.DeleteStrategy

/**
 * @author Dmitry Tsydzik
 * @since 8/16/18
 */
class BrokenAsyncDeleteStrategyTest : DeleteContract, AsyncDeleteContract {
    override fun getStrategy(): DeleteStrategy = BrokenAsyncDeleteStrategy()
}