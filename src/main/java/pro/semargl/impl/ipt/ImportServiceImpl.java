package pro.semargl.impl.ipt;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import pro.semargl.api.ipt.ImportService;
import pro.semargl.api.ipt.WatchServiceWrapper;
import pro.semargl.api.ipt.observer.FileIdentificationObservable;
import pro.semargl.api.ipt.observer.FileIdentificationObserver;
import pro.semargl.api.ipt.parsing.XmlParserService;
import pro.semargl.api.service.ArticleService;
import pro.semargl.api.util.FileManagerService;
import pro.semargl.exception.ServiceException;
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
    private FileManagerService fileManagerService;
    private ArticleService articleService;

    public ImportServiceImpl(WatchServiceWrapper watchService, XmlParserService xmlParserService, FileManagerService fileManagerService, ArticleService articleService) {
        this.watchService = watchService;
        this.xmlParserService = xmlParserService;
        this.fileManagerService = fileManagerService;
        this.articleService = articleService;
    }

    @Override
    public void performImport(Path filePath) {
        LOGGER.debug("performImport(" + filePath + ")");
        List<Article> articleList = xmlParserService.parse(filePath);
        try {
            articleService.saveAll(articleList);
            fileManagerService.remove(filePath);
        } catch (ServiceException e) {
            LOGGER.error("Exception while import performing: ", e);
            fileManagerService.move(filePath);
        }
    }

    @Override
    public void startWatching() {
        LOGGER.debug("startWatching");
        ((FileIdentificationObservable) watchService).addFileIdentificationObserver(this);
        watchService.startWatching();
    }
}
