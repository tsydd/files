package by.tsyd.files.fs.rx

import by.tsyd.files.fs.api.*
import io.reactivex.Observable

/**
 * @author Dmitry Tsydzik
 * @since 7/25/18
 */
interface RxFileSystem {
    fun get(path: Path): Observable<FileSystemItem>

    fun getChildren(path: Path): Observable<String>

    fun createFile(path: Path, content: FileContent): Observable<File>

    fun createDirectory(path: Path): Observable<Directory>
}