package pro.semargl.impl.ipt.parsing.handler;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import pro.semargl.model.Article;
import pro.semargl.model.MeasurementUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler for SAX parsing Article data.
 */
@Service("articleHandler")
public class ArticleHandler extends DefaultHandler {
    private static final Logger LOGGER = Logger.getLogger(ArticleHandler.class);
    private List<Article> articleList = new ArrayList<>();
    private Article article;
    private MeasurementUnit measurementUnit;
    private boolean bTitle = false;
    private boolean bDescription = false;
    private boolean bMeasurementUnit = false;
    private boolean bWeight = false;
    private static final String ARTICLE = "article";
    private static final String TITLE = "Title";
    private static final String DESCRIPTION = "Description";
    private static final String MEASUREMENT_UNIT = "UnitOfMeasurement";
    private static final String WEIGHT = "Weight";

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        LOGGER.debug("call startElement(" + uri + ", " + localName + ", " + qName + ", " + attributes + ")");
        if (ARTICLE.equalsIgnoreCase(qName)) {
            article = new Article();
        } else if (TITLE.equalsIgnoreCase(qName)) {
            bTitle = true;
        } else if (DESCRIPTION.equalsIgnoreCase(qName)) {
            bDescription = true;
        } else if (MEASUREMENT_UNIT.equalsIgnoreCase(qName)) {
            bMeasurementUnit = true;
            measurementUnit = new MeasurementUnit();
        } else if (WEIGHT.equalsIgnoreCase(qName)) {
            bWeight = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        LOGGER.debug("call characters(" + ch + ", " + start + ", " + length + ")");
        if (bTitle) {
            article.setTitle(new String(ch, start, length));
            bTitle = false;
        } else if (bDescription) {
            article.setDescription(new String(ch, start, length));
            bDescription = false;
        } else if (bMeasurementUnit) {
            measurementUnit.setName(new String(ch, start, length));
            article.setMeasurementUnit(measurementUnit);
            bMeasurementUnit = false;
        } else if (bWeight) {
            article.setWeight(Double.valueOf(new String(ch, start, length)));
            bWeight = false;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        LOGGER.debug("call endElement(" + uri + ", " + localName + ", " + qName + ")");
        if (ARTICLE.equalsIgnoreCase(qName)) {
            articleList.add(article);
        }
    }

    public List<Article> getArticleList() {
        return articleList;
    }
}
