package by.tsyd.files.delete.async

import by.tsyd.files.delete.AsyncDeleteContract
import by.tsyd.files.delete.DeleteContract
import by.tsyd.files.delete.DeleteStrategy

internal class KotlinDeleteStrategyTest : DeleteContract, AsyncDeleteContract {
    override fun getStrategy(): DeleteStrategy = KotlinDeleteStrategy()
}