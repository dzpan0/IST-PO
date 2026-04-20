package hva.exceptions;

import java.io.Serial;

public class UnknownVeterinarianKeyException extends Exception {

    @Serial
    private static final long serialVersionUID = 202410242116L;

    private final String _id;

    public UnknownVeterinarianKeyException(String id) {
        _id = id;
    }

    public String getId() {
        return _id;
    }

}
