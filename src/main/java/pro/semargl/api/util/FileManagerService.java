package pro.semargl.api.util;

import java.nio.file.Path;

/**
 * File manager API. Used for remove or move import file.
 */
public interface FileManagerService {
    void move(Path filePath);
    void remove(Path filePath);
}
