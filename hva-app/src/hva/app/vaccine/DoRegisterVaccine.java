package hva.app.vaccine;

import hva.Hotel;
import hva.app.exceptions.DuplicateVaccineKeyException;
import hva.app.exceptions.UnknownSpeciesKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoRegisterVaccine extends Command<Hotel> {

    DoRegisterVaccine(Hotel receiver) {
        super(Label.REGISTER_VACCINE, receiver);
        addStringField("vaccineId", Prompt.vaccineKey());
        addStringField("vaccineName", Prompt.vaccineName());
        addStringField("species", Prompt.listOfSpeciesKeys());
    }

    @Override
    protected final void execute() throws CommandException {
        String vaccineId = stringField("vaccineId");
        String vaccineName = stringField("vaccineName");
        String species = stringField("species");

        try {
            _receiver.registerVaccine(vaccineId, vaccineName, species);
        }
        catch (hva.exceptions.DuplicateVaccineKeyException e) {
            throw new DuplicateVaccineKeyException(e.getId());
        }
        catch (hva.exceptions.UnknownSpeciesKeyException e) {
            throw new UnknownSpeciesKeyException(e.getId());
        }
    }

}
