package by.tsyd.files.delete

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
interface AsyncDeleteContract {
    fun getStrategy(): DeleteStrategy

    @Test
    @JvmDefault
    fun `delete strategy is asynchronous`() {
        val source = BlockingFileSystemImpl().fill(3, 10)

        Assertions.assertTimeout(Duration.ofMillis(1500)) {
            getStrategy().delete(KotlinDelayFileSystem(100, source))
        }

        assertEquals(BlockingFileSystemImpl(), source)
    }
}