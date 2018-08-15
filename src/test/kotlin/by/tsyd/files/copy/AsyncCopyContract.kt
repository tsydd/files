package by.tsyd.files.copy

import by.tsyd.files.dsl.fill
import by.tsyd.files.fs.BlockingFileSystemImpl
import by.tsyd.files.fs.kotlin.KotlinDelayFileSystem
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.Duration
import kotlin.test.assertEquals

/**
 * @author Dmitry Tsydzik
 * @since 8/15/18
 */
interface AsyncCopyContract {
    fun getStrategy(): CopyStrategy

    @Test
    @JvmDefault
    fun `copy strategy is asynchronous`() {
        val source = BlockingFileSystemImpl().fill(3, 10)
        val target = BlockingFileSystemImpl()

        Assertions.assertTimeout(Duration.ofMillis(1500)) {
            getStrategy().copy(KotlinDelayFileSystem(100, source), KotlinDelayFileSystem(100, target))
        }

        assertEquals(source, target)
    }
}