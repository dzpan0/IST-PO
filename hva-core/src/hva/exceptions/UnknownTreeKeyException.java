package hva.exceptions;

import java.io.Serial;

public class UnknownTreeKeyException extends Exception {

    @Serial
    private static final long serialVersionUID = 202410110107L;

    private final String _id;

    public UnknownTreeKeyException(String id) {
        _id = id;
    }

    public String getId() {
        return _id;
    }

}
