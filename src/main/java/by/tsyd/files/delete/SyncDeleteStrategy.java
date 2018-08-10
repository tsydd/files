package by.tsyd.files.delete;

import by.tsyd.files.fs.SlowBlockingFileSystem;
import by.tsyd.files.fs.api.Path;
import by.tsyd.files.fs.kotlin.KotlinFileSystem;
import org.jetbrains.annotations.NotNull;

public class SyncDeleteStrategy implements DeleteStrategy {
    @Override
    public void delete(@NotNull KotlinFileSystem fs, boolean deleteSelf) {
        SlowBlockingFileSystem fsWrapper = new SlowBlockingFileSystem(fs);
        fsWrapper.getChildren(Path.getROOT())
                .forEach(name -> fsWrapper.delete(new Path("/" + name)));
    }
}
