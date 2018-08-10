package by.tsyd.files.fs

import by.tsyd.files.fs.api.*
import by.tsyd.files.getLogger
import java.util.concurrent.ConcurrentHashMap

/**
 * @author Dmitry Tsydzik
 * @since 6/19/18
 */
internal class BlockingFileSystemImpl : BlockingFileSystem {
    sealed class FsNode {

        data class FsFile(val content: FileContent) : FsNode()

        data class FsDirectory(
            val items: ConcurrentHashMap<String, FsNode> = ConcurrentHashMap()
        ) : FsNode()
    }

    private val root = FsNode.FsDirectory()
    private val log = getLogger(javaClass)

    override fun getChildren(path: Path): List<String> {
        log.trace("Getting files list for {}", path)
        return getDir(path)
            .items
            .keys()
            .toList()
    }

    override fun get(path: Path): FileSystemItem {
        log.trace("Get file at path {}", path)
        if (path == Path.ROOT) return Directory(path)
        val fsNode = getDir(path.parent).items[path.name]
            ?: throw FileNotFoundException("File with name ${path.name} not found in directory ${path.parent}")
        return when (fsNode) {
            is FsNode.FsDirectory -> Directory(path)
            is FsNode.FsFile -> File(path, fsNode.content)
        }
    }

    override fun createFile(path: Path, content: FileContent): File {
        log.trace("Creating file at {} with content={}", path, content)
        getDir(path.parent).items[path.name] = FsNode.FsFile(content)
        return File(path, content)
    }

    override fun createDirectory(path: Path): Directory {
        log.trace("Creating directory at {}", path)
        getDir(path.parent).items[path.name] = FsNode.FsDirectory()
        return Directory(path)
    }

    override fun delete(path: Path) {
        log.trace("Deleting {}", path)
        val parentItems = getDir(path.parent).items
        val file = parentItems[path.name] ?: throw FileNotFoundException(path.toString())
        when (file) {
            is FsNode.FsFile -> parentItems.remove(path.name)
            is FsNode.FsDirectory -> {
                if (file.items.isNotEmpty()) throw DirectoryNotEmptyException(path.toString())
                parentItems.remove(path.name)
            }
        }
    }

    private fun getDir(path: Path): FsNode.FsDirectory = path.foldIndexed(root) { index, acc, item ->
        val next = acc.items[item]
        when (next) {
            null -> throw FileNotFoundException("${path.subPath(0, index)} not found")
            is FsNode.FsDirectory -> return@foldIndexed next
            is FsNode.FsFile -> throw FsException("${path.subPath(0, index)} is not a directory")
        }
    }

    override fun equals(other: Any?): Boolean = when (other) {
        is BlockingFileSystemImpl -> root == other.root
        else -> false
    }

    override fun hashCode(): Int = root.hashCode()

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        printDirectory(root, 0, stringBuilder)
        return stringBuilder.toString()
    }

    private fun printDirectory(dir: FsNode.FsDirectory, indent: Int, stringBuilder: StringBuilder) {
        dir
            .items
            .entries
            .asSequence()
            .sortedBy { it.key }
            .forEach { (name, node) ->
                (0 until indent).forEach {
                    stringBuilder.append("  ")
                }
                stringBuilder.append(name).appendln()
                if (node is FsNode.FsDirectory) {
                    printDirectory(node, indent + 1, stringBuilder)
                }
            }
    }
}
