package by.tsyd.files.delete.blocking

import by.tsyd.files.delete.DeleteStrategy
import by.tsyd.files.fs.SlowBlockingFileSystem
import by.tsyd.files.fs.api.BlockingFileSystem
import by.tsyd.files.fs.api.Directory
import by.tsyd.files.fs.api.File
import by.tsyd.files.fs.api.Path
import by.tsyd.files.fs.kotlin.KotlinFileSystem

class KotlinBlockingDeleteStrategy : DeleteStrategy {
    override fun delete(fs: KotlinFileSystem, deleteSelf: Boolean) {
        delete(Path.ROOT, deleteSelf, SlowBlockingFileSystem(fs))
    }

    private fun delete(path: Path, deleteSelf: Boolean, fs: BlockingFileSystem) {
        val file = fs.get(path)
        when (file) {
            is File -> fs.delete(path)
            is Directory -> {
                fs.getChildren(path)
                    .forEach {
                        delete(path + it, true, fs)
                    }
                if (deleteSelf) {
                    fs.delete(path)
                }
            }
        }
    }
}