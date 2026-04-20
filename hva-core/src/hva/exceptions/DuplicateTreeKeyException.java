package hva.exceptions;

import java.io.Serial;

public class DuplicateTreeKeyException extends Exception {

    @Serial
    private static final long serialVersionUID = 202410102027L;

    private final String _id;

    public DuplicateTreeKeyException(String id) {
        _id = id;
    }

    public String getId() {
        return _id;
    }

}
