package pro.semargl.api.ipt.observer;

import java.nio.file.Path;

/**
 * File identification observable API
 */
public interface FileIdentificationObservable {
    void addFileIdentificationObserver(FileIdentificationObserver observer);
    void removeFileIdentificationObserver(FileIdentificationObserver observer);
    void fileIdentified(Path filePath);
}
