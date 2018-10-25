package pro.semargl.api.util;

import java.nio.file.Path;

/**
 * Verify file content type functionality API
 */
public interface ContentTypeResolver {
    /**
     * Verify file content type.
     *
     * @param requiredType required MIME type
     * @param filePath     verifiable file path
     * @return
     */
    boolean isRequiredType(String requiredType, Path filePath);
}
