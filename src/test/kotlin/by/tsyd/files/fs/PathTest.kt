package by.tsyd.files.fs

import by.tsyd.files.fs.api.Path
import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test
import kotlin.test.asserter

/**
 * @author Dmitry Tsydzik
 * @since 7/4/18
 */
internal class PathTest {

    @Test
    internal fun `root#toString`() {
        asserter.assertEquals(null, "/", Path.ROOT.toString())
    }

    @Test
    internal fun `parse relative path fails`() {
        assertThrows(AssertionError::class.java) {
            Path("a/b/c")
        }
    }

    @Test
    internal fun `parse root`() {
        asserter.assertEquals(null, Path.ROOT, Path("/"))
    }

    @Test
    internal fun `parse path with empty token fails`() {
        assertThrows(AssertionError::class.java) {
            Path("/a//b")
        }
    }

    @Test
    internal fun `parse path`() {
        val stringPath = "/a/b/c"
        val path = Path(stringPath)
        asserter.assertEquals(null, stringPath, path.toString())
        asserter.assertEquals(null, Path(listOf("a", "b", "c")), path)
    }

    @Test
    internal fun parent() {
        asserter.assertEquals(null, Path("/a/b"), Path("/a/b/c").parent)
    }

    @Test
    internal fun name() {
        asserter.assertEquals(null, "c", Path("/a/b/c").name)
    }

    @Test
    internal fun plus() {
        asserter.assertEquals(null, Path("/a/b/c"), Path("/a/b") + "c")
    }
}