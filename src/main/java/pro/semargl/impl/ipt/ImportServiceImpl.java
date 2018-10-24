package pro.semargl.impl.ipt;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import pro.semargl.api.ipt.ImportService;
import pro.semargl.api.ipt.WatchServiceWrapper;
import pro.semargl.api.ipt.observer.FileIdentificationObservable;
import pro.semargl.api.ipt.observer.FileIdentificationObserver;

import java.nio.file.Path;

/**
 * Import functionality.
 */
@Service("importService")
public class ImportServiceImpl implements ImportService, FileIdentificationObserver {
    private static final Logger LOGGER = Logger.getLogger(ImportServiceImpl.class);
    private WatchServiceWrapper watchService;

    public ImportServiceImpl(WatchServiceWrapper watchService) {
        this.watchService = watchService;
    }

    @Override
    public void performImport(Path filePath) {
        LOGGER.debug("performImport(" + filePath + ")");
    }

    @Override
    public void startImport() {
        LOGGER.debug("startImport");
        //todo: get from property file
        watchService.setWatchableDirectoryPath("F:\\Git\\github\\semargl\\import_export_module\\tmp");
        ((FileIdentificationObservable)watchService).addFileIdentificationObserver(this);
        watchService.startWatch();
    }
}
