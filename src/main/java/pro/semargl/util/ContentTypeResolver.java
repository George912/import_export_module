package pro.semargl.util;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Verify file content type functionality.
 */
@Service("contentTypeResolver")
public class ContentTypeResolver {
    private static final Logger LOGGER = Logger.getLogger(ContentTypeResolver.class);

    public ContentTypeResolver() {
    }

    /**
     * Verify file content type.
     *
     * @param requiredType required MIME type
     * @param filePath     verifiable file path
     * @return
     */
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
