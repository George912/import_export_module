package pro.semargl.util;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import pro.semargl.api.util.FileManagerService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service("fileManagerService")
public class FileManagerServiceImpl implements FileManagerService {
    private static final Logger LOGGER = Logger.getLogger(FileManagerServiceImpl.class);

    @Override
    public void move(Path filePath) {
        LOGGER.debug("call move(" + filePath + ")");
        //todo: get from properties file
        Path destinationPath = Paths.get("F:\\Git\\github\\semargl\\import_export_module\\tmp\\err\\" + filePath.getFileName());
        try {
            Files.move(filePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            LOGGER.error("Exception while file moving: " + e);
        }
    }

    @Override
    public void remove(Path filePath) {
        LOGGER.debug("call remove(" + filePath + ")");
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            LOGGER.error("Exception while file removing: " + e);
        }
    }
}
