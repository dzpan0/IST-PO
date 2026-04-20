package hva.exceptions;

import java.io.Serial;

public class UnknownVaccineKeyException extends Exception {

    @Serial
    private static final long serialVersionUID = 202410242140L;

    private final String _id;

    public UnknownVaccineKeyException(String id) {
        _id = id;
    }

    public String getId() {
        return _id;
    }

}
