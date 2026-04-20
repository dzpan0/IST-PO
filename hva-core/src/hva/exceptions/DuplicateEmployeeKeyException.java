package hva.exceptions;

import java.io.Serial;

public class DuplicateEmployeeKeyException extends Exception {

    @Serial
    private static final long serialVersionUID = 202410100224L;

    private final String _id;

    public DuplicateEmployeeKeyException(String id) {
        _id = id;
    }

    public String getId() {
        return _id;
    }

}
