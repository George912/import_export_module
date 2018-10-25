package pro.semargl.impl.ept;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import pro.semargl.api.ept.ExportService;
import pro.semargl.api.model.ArticleListWrapper;
import pro.semargl.model.Article;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service("exportService")
public class ExportServiceImpl implements ExportService {
    private static final Logger LOGGER = Logger.getLogger(ExportServiceImpl.class);
    private XmlMapper xmlMapper;
    private ArticleListWrapper articleListWrapper;

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
            xmlMapper.writeValue(new File("sample.xml"), articleListWrapper);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
