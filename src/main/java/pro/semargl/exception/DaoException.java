package pro.semargl.exception;

/**
 * Signals an exception in the dao layer.
 */
public class DaoException extends Exception{
    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}