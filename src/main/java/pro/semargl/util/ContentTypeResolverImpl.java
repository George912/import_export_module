package pro.semargl.util;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import pro.semargl.api.util.ContentTypeResolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service("contentTypeResolver")
public class ContentTypeResolverImpl implements ContentTypeResolver {
    private static final Logger LOGGER = Logger.getLogger(ContentTypeResolverImpl.class);

    public ContentTypeResolverImpl() {
    }

    @Override
    public boolean isRequiredType(String requiredType, Path filePath) {
        LOGGER.debug("call isRequiredType(" + requiredType + ", " + filePath + ")");
        try {
            if (Files.probeContentType(filePath).equals(requiredType)) {
                LOGGER.debug("New file " + filePath + " type is " + requiredType);
                return true;
            }
        } catch (IOException x) {
            LOGGER.error("Exception while resolve file content type: " + x);
        }
        LOGGER.debug("New file " + filePath + " type is not " + requiredType);
        return false;
    }
}
