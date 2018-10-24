package pro.semargl.impl.ipt;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import pro.semargl.api.ipt.observer.FileIdentificationObservable;
import pro.semargl.api.ipt.observer.FileIdentificationObserver;
import pro.semargl.api.ipt.WatchServiceWrapper;
import pro.semargl.util.ContentTypeResolver;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

@Service("watchService")
public class WatchServiceWrapperImpl implements WatchServiceWrapper, FileIdentificationObservable {
    private static final Logger LOGGER = Logger.getLogger(WatchServiceWrapperImpl.class);
    private WatchService watcher;
    private Path dir;
    private String watchableDirectoryPath;
    private WatchKey key;
    private ContentTypeResolver contentTypeResolver;
    private List<FileIdentificationObserver> observers;

    public WatchServiceWrapperImpl() {
        observers = new ArrayList();
        try {
            watcher = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            LOGGER.error("Exception while initialization watch service: " + e);
        }
    }

    @Autowired
    public WatchServiceWrapperImpl(ContentTypeResolver contentTypeResolver) {
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
                    fileIdentified(child);
                }
            }

            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
    }

    @Override
    public void startWatch() {
        LOGGER.debug("call startWatch()");
        registerPath();
        watch();
    }

    @Override
    public void addFileIdentificationObserver(FileIdentificationObserver observer) {
        LOGGER.debug("call addFileIdentificationObserver(" + observer + ")");
        if(!observers.contains(observer)){
            observers.add(observer);
        }
    }

    @Override
    public void removeFileIdentificationObserver(FileIdentificationObserver observer) {
        LOGGER.debug("call removeFileIdentificationObserver(" + observer + ")");
        observers.remove(observer);
    }

    @Override
    public void fileIdentified(Path filePath) {
        LOGGER.debug("call fileIdentified(" + filePath + ")");
        observers.forEach(observer->observer.performImport(filePath));
    }
}