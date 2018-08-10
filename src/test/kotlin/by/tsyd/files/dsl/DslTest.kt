package by.tsyd.files.dsl

import by.tsyd.files.fs.BlockingFileSystemImpl
import by.tsyd.files.fs.api.BlockingFileSystem
import by.tsyd.files.fs.api.Path
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class DslTest {

    companion object {
        @JvmStatic
        internal fun test(): Stream<Arguments> = Stream.of(
            Arguments.of(fs {
                dir("a")
                file("b", "content")
                dir("c") {
                    dir("d")
                    file("e")
                }
            }, BlockingFileSystemImpl().apply {
                createDirectory(Path("/a"))
                createFile(Path("/b"), "content")
                createDirectory(Path("/c"))
                createDirectory(Path("/c/d"))
                createFile(Path("/c/e"))
            }),
            Arguments.of(fs {
                dir("file0") {
                    dir("file0")
                    dir("file1")
                }
                dir("file1") {
                    dir("file0")
                    dir("file1")
                }
            }, BlockingFileSystemImpl().fill(2, 2))
        )
    }

    @ParameterizedTest
    @MethodSource
    internal fun test(actual: BlockingFileSystem, expected: BlockingFileSystem) {
        assertEquals(expected, actual)
    }
}