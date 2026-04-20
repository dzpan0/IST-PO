package hva.exceptions;

import java.io.Serial;

public class UnknownSpeciesKeyException extends Exception {

    @Serial
    private static final long serialVersionUID = 202410100007L;

    private final String _id;

    public UnknownSpeciesKeyException(String id) {
        _id = id;
    }

    public String getId() {
        return _id;
    }

}
