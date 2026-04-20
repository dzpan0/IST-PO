package hva.exceptions;

import java.io.Serial;

public class DuplicateHabitatKeyException extends Exception {

    @Serial
    private static final long serialVersionUID = 202410101209L;

    private final String _id;

    public DuplicateHabitatKeyException(String id) {
        _id = id;
    }

    public String getId() {
        return _id;
    }

}
