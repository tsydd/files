package by.tsyd.files.copy.async

import by.tsyd.files.copy.CopyStrategy
import by.tsyd.files.fs.api.Directory
import by.tsyd.files.fs.api.File
import by.tsyd.files.fs.api.Path
import by.tsyd.files.fs.kotlin.KotlinFileSystem
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

class KotlinCopyStrategy : CopyStrategy {
    override fun copy(sourceFs: KotlinFileSystem, targetFs: KotlinFileSystem) {
        runBlocking {
            copy(sourceFs, targetFs, Path.ROOT)
        }
    }

    private suspend fun copy(sourceFs: KotlinFileSystem, targetFs: KotlinFileSystem, path: Path) {
        coroutineScope {
            val jobs = sourceFs.getChildren(path).map {
                async {
                    when (val file = sourceFs.get(path + it)) {
                        is File -> targetFs.createFile(file.path, file.content)
                        is Directory -> {
                            targetFs.createDirectory(file.path)
                            copy(sourceFs, targetFs, file.path)
                        }
                    }
                }
            }
            jobs.awaitAll()
        }
    }
}
