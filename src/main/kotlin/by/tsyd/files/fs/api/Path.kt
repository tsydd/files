package by.tsyd.files.fs.api

/**
 * @author Dmitry Tsydzik
 * @since 6/20/18
 */
data class Path(
    private val items: List<String>
) : Iterable<String> {
    constructor(path: String) : this(getTokens(path))

    companion object {
        @JvmStatic
        val ROOT = Path(listOf())

        private fun getTokens(path: String): List<String> {

            assert(path.startsWith("/")) {
                "only absolute paths are supported"
            }
            if (path == "/") {
                return emptyList()
            }
            return path
                .splitToSequence("/")
                .drop(1)
                .toList()
        }
    }

    init {
        items.forEachIndexed { index, item ->
            assert(item.isNotBlank()) {
                "blank item at position #$index"
            }
        }
    }

    operator fun plus(item: String): Path = Path(items + item)

    override fun iterator() = items.iterator()

    val name: String
        get() = if (items.isEmpty()) "/" else items.last()

    val parent: Path
        get() {
            assert(items.isNotEmpty()) { "Root directory has no parent" }
            return Path(items.subList(0, items.size - 1))
        }

    override fun toString(): String = "/" + items.joinToString("/")

    fun subPath(first: Int, last: Int): Path =
        Path(items.subList(first, last))
}
