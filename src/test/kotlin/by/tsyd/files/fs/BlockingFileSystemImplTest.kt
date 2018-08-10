package by.tsyd.files.fs

import by.tsyd.files.fs.api.*
import org.junit.jupiter.api.Assertions.*
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.asserter

/**
 * @author Dmitry Tsydzik
 * @since 7/4/18
 */
internal class BlockingFileSystemImplTest {
    private lateinit var fs: BlockingFileSystemImpl

    @BeforeTest
    internal fun setUp() {
        fs = BlockingFileSystemImpl()
    }

    @Test
    internal fun empty() {
        asserter.assertEquals(null, listOf<FileSystemItem>(), fs.getChildren(Path("/")))
    }

    @Test
    internal fun `add directory to root`() {
        fs.createDirectory(Path("/a"))

        asserter.assertEquals(null, listOf("a"), fs.getChildren(Path("/")))
        asserter.assertEquals(null, Directory(Path("/a")), fs.get(Path("/a")))
    }

    @Test
    internal fun `add file to root`() {
        fs.createFile(Path("/a"))

        asserter.assertEquals(null, listOf("a"), fs.getChildren(Path("/")))
        asserter.assertEquals(null, File(Path("/a")), fs.get(Path("/a")))
    }

    @Test
    internal fun `get non-existing file fails`() {
        assertThrows(FileNotFoundException::class.java) {
            fs.getChildren(Path("/a"))
        }
    }
}