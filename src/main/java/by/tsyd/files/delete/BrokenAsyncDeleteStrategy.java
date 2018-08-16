package by.tsyd.files.delete;

import by.tsyd.files.fs.api.Path;
import by.tsyd.files.fs.async.AsyncFileSystemImpl;
import by.tsyd.files.fs.kotlin.KotlinFileSystem;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Dmitry Tsydzik
 * @since 8/16/18
 */
public class BrokenAsyncDeleteStrategy implements DeleteStrategy {
    private static final Logger log = LoggerFactory.getLogger(BrokenAsyncDeleteStrategy.class);

    @Override
    public void delete(@NotNull KotlinFileSystem fs, boolean deleteSelf) {
        AsyncFileSystemImpl fsWrapper = new AsyncFileSystemImpl(fs);

        CountDownLatch latch = new CountDownLatch(1);

        fsWrapper.getChildren(Path.getROOT(), getChildrenResult -> {
            if (getChildrenResult.isOk()) {
                List<String> childrenNames = getChildrenResult.asSuccess().getData();
                if (childrenNames.isEmpty()) {
                    latch.countDown();
                    return null;
                }

                AtomicInteger counter = new AtomicInteger(childrenNames.size());
                childrenNames.forEach(childName -> {
                    fsWrapper.delete(new Path("/" + childName), deleteFileResult -> {
                        if (counter.decrementAndGet() == 0) {
                            latch.countDown();
                        }
                        return null;
                    });
                });
                return null;
            } else {
                latch.countDown();
                return null;
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            log.info("Computation was interrupted", e);
        }
    }
}
