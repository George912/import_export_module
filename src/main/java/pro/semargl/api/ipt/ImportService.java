package pro.semargl.api.ipt;

/**
 * Import functionality API.
 */
public interface ImportService {
    /**
     * Start directory watching, xml parsing, store data to database
     */
    void startImport();
}
