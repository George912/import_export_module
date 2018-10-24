package pro.semargl.impl.ipt.parsing;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import pro.semargl.api.ipt.parsing.XmlParserService;
import pro.semargl.impl.ipt.parsing.handler.ArticleHandler;
import pro.semargl.model.Article;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service("xmlParserService")
public class XmlParserServiceImpl implements XmlParserService {
    private static final Logger LOGGER = Logger.getLogger(XmlParserServiceImpl.class);
    private DefaultHandler handler;

    public XmlParserServiceImpl(ArticleHandler handler) {
        this.handler = handler;
    }

    @Override
    public List<Article> parse(Path filePath) {
        LOGGER.debug("parse(" + filePath + ")");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        List<Article> articleList = new ArrayList<>();
        try {
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(filePath.toFile(), handler);
            articleList = ((ArticleHandler) handler).getArticleList();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOGGER.debug("Exception while parsing: " + e);
            return articleList;
        }
        return articleList;
    }
}
