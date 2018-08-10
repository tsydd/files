package by.tsyd.files.delete.blocking

import by.tsyd.files.delete.DeleteContract
import by.tsyd.files.delete.DeleteStrategy

class KotlinBlockingDeleteStrategyTest : DeleteContract {
    override fun getStrategy(): DeleteStrategy = KotlinBlockingDeleteStrategy()
}