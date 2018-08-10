package by.tsyd.files.delete

import by.tsyd.files.fs.BlockingFileSystemImpl
import by.tsyd.files.fs.api.BlockingFileSystem
import by.tsyd.files.fs.kotlin.KotlinDelayFileSystem
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

interface DeleteContract {

    companion object {
        private const val DELAY: Long = 1
    }

    fun getStrategy(): DeleteStrategy

    @ParameterizedTest
    @JvmDefault
    @MethodSource("by.tsyd.files.FsArgumentsKt#arguments")
    fun instantDeleteTest(fs: BlockingFileSystem) {
        getStrategy().delete(KotlinDelayFileSystem(0, fs))
        assertEquals(BlockingFileSystemImpl(), fs)
    }

    @ParameterizedTest
    @JvmDefault
    @MethodSource("by.tsyd.files.FsArgumentsKt#arguments")
    fun delayDeleteTest(fs: BlockingFileSystem) {
        getStrategy().delete(KotlinDelayFileSystem(DELAY, fs))
        assertEquals(BlockingFileSystemImpl(), fs)
    }
}
