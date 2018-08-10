package by.tsyd.files.delete.async

import by.tsyd.files.delete.DeleteStrategy
import by.tsyd.files.fs.api.Directory
import by.tsyd.files.fs.api.File
import by.tsyd.files.fs.api.Path
import by.tsyd.files.fs.kotlin.KotlinFileSystem
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

class KotlinDeleteStrategy : DeleteStrategy {
    override fun delete(fs: KotlinFileSystem, deleteSelf: Boolean) {
        runBlocking {
            delete(fs, deleteSelf, Path.ROOT)
        }
    }

    private suspend fun delete(fs: KotlinFileSystem, deleteSelf: Boolean, path: Path): Unit = coroutineScope {
        when (fs.get(path)) {
            is File -> fs.delete(path)
            is Directory -> {
                val jobs = fs.getChildren(path)
                    .map { async { delete(fs, true, path + it) } }
                jobs.awaitAll()
                if (deleteSelf) {
                    fs.delete(path)
                }
            }
        }
    }
}
