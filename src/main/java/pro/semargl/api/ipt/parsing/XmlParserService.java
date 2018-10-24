package pro.semargl.api.ipt.parsing;

import pro.semargl.model.Article;

import java.nio.file.Path;
import java.util.List;

/**
 * XML parsing API
 */
public interface XmlParserService {
    /**
     * Parse XML, build VendorCode instances, store in list
     *
     * @param filePath XML file path for parsing
     * @return VendorCode instances list
     */
    List<Article> parse(Path filePath);
}
