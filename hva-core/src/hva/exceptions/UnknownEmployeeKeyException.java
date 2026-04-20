package hva.exceptions;

import java.io.Serial;

public class UnknownEmployeeKeyException extends Exception {

    @Serial
    private static final long serialVersionUID = 202410110130L;

    private final String _id;

    public UnknownEmployeeKeyException(String id) {
        _id = id;
    }

    public String getId() {
        return _id;
    }

}
