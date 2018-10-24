package pro.semargl.ipt;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import pro.semargl.util.ContentTypeResolver;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

/**
 * Implementation file change notification functionality.
 * Responsibility:
 * - file change notification
 * - xmlFilePath spittle
 */
@Service("watchServiceWrapper")
public class WatchServiceWrapper {
    private static final Logger LOGGER = Logger.getLogger(WatchServiceWrapper.class);
    private WatchService watcher;
    private Path dir;
    private String watchableDirectoryPath;
    private WatchKey key;
    private ContentTypeResolver contentTypeResolver;

    public WatchServiceWrapper() {
        try {
            watcher = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            LOGGER.error("Exception while initialization watch service: " + e);
        }
    }

    @Autowired
    public WatchServiceWrapper(ContentTypeResolver contentTypeResolver) {
        this();
        this.contentTypeResolver = contentTypeResolver;
    }

    public void setWatchableDirectoryPath(String watchableDirectoryPath) {
        this.watchableDirectoryPath = watchableDirectoryPath;
    }

    /**
     * Register directory path for watch.
     */
    private void registerPath() {
        LOGGER.debug("call registerPath()");
        dir = Paths.get(watchableDirectoryPath);
        try {
            key = dir.register(watcher, ENTRY_CREATE);
        } catch (IOException x) {
            LOGGER.debug("Exception while register directory path: " + x);
        }
    }

    /**
     * File change notification logic.
     */
    private void watch() {
        LOGGER.debug("call watch()");
        for (; ; ) {
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                LOGGER.error("Exception while retrieving watch key: " + x);
                return;
            }

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
                if (kind == OVERFLOW) {
                    continue;
                }
                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                Path filename = ev.context();
                Path child = dir.resolve(filename);
                if (contentTypeResolver.isRequiredType(MimeTypeUtils.TEXT_XML_VALUE, child)) {
                    //todo: call observer method
                }
            }

            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
    }

    /**
     * Register required directory path and start watch.
     */
    public void startWatch() {
        LOGGER.debug("call startWatch()");
        registerPath();
        watch();
    }
}