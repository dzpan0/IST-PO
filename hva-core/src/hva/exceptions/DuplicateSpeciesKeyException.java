package hva.exceptions;

import java.io.Serial;

public class DuplicateSpeciesKeyException extends Exception {

    @Serial
    private static final long serialVersionUID = 202410240209L;

    private final String _id;

    public DuplicateSpeciesKeyException(String id) {
        _id = id;
    }

    public String getId() {
        return _id;
    }

}
