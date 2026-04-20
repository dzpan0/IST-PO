package hva.exceptions;

import java.io.Serial;

public class NonPositiveIntegerException extends Exception {

    @Serial
    private static final long serialVersionUID = 202410101229L;

    private final int _integer;

    public NonPositiveIntegerException(int integer) {
        _integer = integer;
    }

    public int getArea() {
        return _integer;
    }

}
