package by.tsyd.files.copy

import by.tsyd.files.fs.BlockingFileSystemImpl
import by.tsyd.files.fs.api.BlockingFileSystem
import by.tsyd.files.fs.kotlin.KotlinDelayFileSystem
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

interface CopyContract {
    companion object {
        private const val DELAY: Long = 1
    }

    fun getStrategy(): CopyStrategy

    @ParameterizedTest
    @JvmDefault
    @MethodSource("by.tsyd.files.FsArgumentsKt#arguments")
    fun testInstantFs(fs: BlockingFileSystem) {
        val strategy = getStrategy()
        val target = BlockingFileSystemImpl()
        strategy.copy(
            sourceFs = KotlinDelayFileSystem(0, fs),
            targetFs = KotlinDelayFileSystem(0, target)
        )
        assertEquals(fs, target)
    }

    @ParameterizedTest
    @JvmDefault
    @MethodSource("by.tsyd.files.FsArgumentsKt#arguments")
    fun testSlowFs(fs: BlockingFileSystem) {
        val strategy = getStrategy()
        val target = BlockingFileSystemImpl()
        strategy.copy(
            sourceFs = KotlinDelayFileSystem(DELAY, fs),
            targetFs = KotlinDelayFileSystem(DELAY, target)
        )
        assertEquals(fs, target)
    }
}
