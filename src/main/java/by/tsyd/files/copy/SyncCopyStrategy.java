package by.tsyd.files.copy;

import by.tsyd.files.fs.SlowBlockingFileSystem;
import by.tsyd.files.fs.api.Directory;
import by.tsyd.files.fs.api.File;
import by.tsyd.files.fs.api.FileSystemItem;
import by.tsyd.files.fs.api.Path;
import by.tsyd.files.fs.kotlin.KotlinFileSystem;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dmitry Tsydzik
 * @since 7/16/18
 */
public class SyncCopyStrategy implements CopyStrategy {
    @Override
    public void copy(@NotNull KotlinFileSystem sourceFs, @NotNull KotlinFileSystem targetFs) {
        SlowBlockingFileSystem sourceFsWrapper = new SlowBlockingFileSystem(sourceFs);
        SlowBlockingFileSystem targetFsWrapper = new SlowBlockingFileSystem(targetFs);

        sourceFsWrapper.getChildren(Path.getROOT())
                .forEach(name -> {
                    Path path = new Path("/" + name);
                    FileSystemItem fileSystemItem = sourceFsWrapper.get(path);
                    if (fileSystemItem instanceof File) {
                        targetFsWrapper.createFile(path, ((File) fileSystemItem).getContent());
                    } else if (fileSystemItem instanceof Directory) {
                        targetFsWrapper.createDirectory(path);
                    }
                });
    }
}
