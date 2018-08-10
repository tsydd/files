package by.tsyd.files.copy.blocking

import by.tsyd.files.copy.CopyContract
import by.tsyd.files.copy.CopyStrategy

internal class KotlinBlockingCopyStrategyTest : CopyContract {
    override fun getStrategy(): CopyStrategy = KotlinBlockingCopyStrategy()
}