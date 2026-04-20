package hva.exceptions;

import java.io.Serial;

public class VeterinarianNotAuthorizedException extends Exception {

    @Serial
    private static final long serialVersionUID = 202410242050L;

    private final String _veterinarianId;
    private final String _speciesId;

    public VeterinarianNotAuthorizedException(String veterinarianId, String speciesId) {
        _veterinarianId = veterinarianId;
        _speciesId = speciesId;
    }

    public String getVeterinarianId() {
        return _veterinarianId;
    }

    public String getSpeciesId() {
        return _speciesId;
    }

}
