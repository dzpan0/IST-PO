package hva.exceptions;

import java.io.Serial;

public class DuplicateVaccineKeyException extends Exception {

    @Serial
    private static final long serialVersionUID = 202410101242L;

    private final String _id;

    public DuplicateVaccineKeyException(String id) {
        _id = id;
    }

    public String getId() {
        return _id;
    }

}
