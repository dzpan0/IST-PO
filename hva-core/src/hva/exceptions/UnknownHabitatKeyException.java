package hva.exceptions;

import java.io.Serial;

public class UnknownHabitatKeyException extends Exception {

    @Serial
    private static final long serialVersionUID = 202410092218L;

    private final String _id;

    public UnknownHabitatKeyException(String id) {
        _id = id;
    }

    public String getId() {
        return _id;
    }

}
