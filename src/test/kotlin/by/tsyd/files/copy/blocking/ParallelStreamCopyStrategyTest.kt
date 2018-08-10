package by.tsyd.files.copy.blocking

import by.tsyd.files.copy.CopyContract
import by.tsyd.files.copy.CopyStrategy
import org.junit.jupiter.api.Assertions.*

internal class ParallelStreamCopyStrategyTest : CopyContract {
    override fun getStrategy(): CopyStrategy = ParallelStreamCopyStrategy()
}