package by.tsyd.files.dsl

import by.tsyd.files.fs.BlockingFileSystemImpl
import by.tsyd.files.fs.api.BlockingFileSystem
import by.tsyd.files.fs.api.FileContent
import by.tsyd.files.fs.api.Path

data class DirectoryContext(
    val path: Path,
    val fs: BlockingFileSystem
) {

    fun dir(name: String) {
        fs.createDirectory(path + name)
    }

    fun dir(name: String, init: DirectoryContext.() -> Unit) {
        fs.createDirectory(path + name)
        val dir = DirectoryContext(path + name, fs)
        dir.init()
    }

    fun file(name: String, content: FileContent = null) {
        fs.createFile(path + name, content)
    }
}

fun fs(fs: BlockingFileSystem = BlockingFileSystemImpl(), init: DirectoryContext.() -> Unit): BlockingFileSystem {
    val root = DirectoryContext(Path.ROOT, fs)
    root.init()
    return fs
}

fun BlockingFileSystem.fill(depth: Int, count: Int): BlockingFileSystem {
    fill(this, Path.ROOT, depth, count)
    return this
}

fun fill(fs: BlockingFileSystem, path: Path, depth: Int, count: Int) {
    if (depth == 0) {
        return
    }
    (0 until count).forEach {
        val childPath = path + "file$it"
        fs.createDirectory(childPath)
        fill(fs, childPath, depth - 1, count)
    }
}