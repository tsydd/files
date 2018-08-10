package by.tsyd.files

import by.tsyd.files.dsl.fill
import by.tsyd.files.dsl.fs
import by.tsyd.files.fs.BlockingFileSystemImpl
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

fun arguments(): Stream<Arguments> = Stream.of(
    fs {},
    fs {
        file("a", "content")
    },
    fs {
        dir("dir1")
    },
    fs {
        dir("dir1") {
            dir("dir inside")
        }
    },
    fs {
        dir("a")
        file("b", "content")
        dir("c") {
            dir("d")
            file("e")
        }
    },
    BlockingFileSystemImpl().fill(2, 10)
).map { Arguments.of(it) }