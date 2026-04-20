package hva.exceptions;

import java.io.Serial;

public class DuplicateAnimalKeyException extends Exception{

    @Serial
    private static final long serialVersionUID = 202410092211L;

    private final String _id;

    public DuplicateAnimalKeyException(String id) {
        _id = id;
    }

    public String getId() {
        return _id;
    }

}
