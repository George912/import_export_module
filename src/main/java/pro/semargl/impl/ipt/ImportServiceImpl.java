package pro.semargl.impl.ipt;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import pro.semargl.api.ipt.ImportService;
import pro.semargl.api.ipt.WatchServiceWrapper;
import pro.semargl.api.ipt.observer.FileIdentificationObservable;
import pro.semargl.api.ipt.observer.FileIdentificationObserver;
import pro.semargl.api.ipt.parsing.XmlParserService;
import pro.semargl.model.Article;

import java.nio.file.Path;
import java.util.List;

/**
 * Import functionality.
 */
@Service("importService")
public class ImportServiceImpl implements ImportService, FileIdentificationObserver {
    private static final Logger LOGGER = Logger.getLogger(ImportServiceImpl.class);
    private WatchServiceWrapper watchService;
    private XmlParserService xmlParserService;

    public ImportServiceImpl(WatchServiceWrapper watchService, XmlParserService xmlParserService) {
        this.watchService = watchService;
        this.xmlParserService = xmlParserService;
    }

    @Override
    public void performImport(Path filePath) {
        LOGGER.debug("performImport(" + filePath + ")");
        List<Article> articleList = xmlParserService.parse(filePath);
    }

    @Override
    public void startImport() {
        LOGGER.debug("startImport");
        //todo: get from property file
        watchService.setWatchableDirectoryPath("F:\\Git\\github\\semargl\\import_export_module\\tmp");
        ((FileIdentificationObservable) watchService).addFileIdentificationObserver(this);
        watchService.startWatch();
    }
}
