package seedu.duke.exception;

/**
 * The class DukeException is a subclass of Exception.
 * It is thrown when an error specific to Duke occurs.
 */
public class DukeException extends Exception {
    private DukeExceptionType dukeExceptionType;
    private String info;

    /**
     * Constructs a new DukeException instance by storing the given DukeExceptionType.
     *
     * @param dukeExceptionType The type of dukeException.
     */
    public DukeException(DukeExceptionType dukeExceptionType) {
        this.dukeExceptionType = dukeExceptionType;
    }

    public DukeException(DukeExceptionType dukeExceptionType, String info) {
        this.dukeExceptionType = dukeExceptionType;
        this.info = info;
    }

    /**
     * Returns the dukeExceptionType of the dukeException.
     *
     * @return the dukeExceptionType.
     */
    public DukeExceptionType getError() {
        return dukeExceptionType;
    }

    public String getInfo() {
        return info;
    }


}
