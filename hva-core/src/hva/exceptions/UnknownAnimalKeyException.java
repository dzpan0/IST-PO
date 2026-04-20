package hva.exceptions;

import java.io.Serial;

public class UnknownAnimalKeyException extends Exception {

    @Serial
    private static final long serialVersionUID = 202410240526L;

    private final String _id;

    public UnknownAnimalKeyException(String id) {
        _id = id;
    }

    public String getId() {
        return _id;
    }

}
