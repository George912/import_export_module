package pro.semargl.api.ipt;

/**
 * File change notification functionality API.
 */
public interface WatchServiceWrapper {
    /**
     * Register required directory path and start watch.
     */
    void startWatch();

    void setWatchableDirectoryPath(String watchableDirectoryPath);
}
