package hva.exceptions;

import java.io.Serial;

public class DuplicateSpeciesNameException extends Exception {

    @Serial
    private static final long serialVersionUID = 202410240125L;

    private final String _name;

    public DuplicateSpeciesNameException(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

}
