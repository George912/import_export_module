package pro.semargl.impl.ept;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.semargl.api.ept.ExportService;
import pro.semargl.api.model.ArticleListWrapper;
import pro.semargl.model.Article;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("exportService")
public class ExportServiceImpl implements ExportService {
    private static final Logger LOGGER = Logger.getLogger(ExportServiceImpl.class);
    private XmlMapper xmlMapper;
    private ArticleListWrapper articleListWrapper;
    @Value("${export.filePrefix}")
    private String exportFilePrefix;
    @Value("${export.fileSuffix}")
    private String exportFileSuffix;
    @Value("${export.directory}")
    private String exportDirectory;
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH-mm-ss";


    public ExportServiceImpl(XmlMapper xmlMapper, ArticleListWrapper articleListWrapper) {
        this.xmlMapper = xmlMapper;
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.articleListWrapper = articleListWrapper;
    }

    @Override
    public void export(List<Article> articleList) {
        LOGGER.debug("call export(" + articleList + ")");
        articleListWrapper.setArticleList(articleList);
        try {
            xmlMapper.writeValue(new File(buildExportFileName()), articleListWrapper);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String buildExportFileName() {
        LOGGER.debug("call buildExportFileName()");
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        DateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
        Date date = new Date();
        return new StringBuilder(exportDirectory)
                .append(exportFilePrefix)
                .append("_")
                .append(dateFormat.format(date))
                .append("_")
                .append(timeFormat.format(date))
                .append(exportFileSuffix).toString();
    }
}
