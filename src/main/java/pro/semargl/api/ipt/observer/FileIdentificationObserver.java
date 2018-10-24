package pro.semargl.api.ipt.observer;

import java.nio.file.Path;

/**
  * File identification observer API
 */
public interface FileIdentificationObserver {
    void performImport(Path filePath);
}
