package hva.app.vaccine;

import hva.Hotel;
import hva.app.exceptions.UnknownAnimalKeyException;
import hva.app.exceptions.UnknownEmployeeKeyException;
import hva.app.exceptions.UnknownVaccineKeyException;
import hva.app.exceptions.UnknownVeterinarianKeyException;
import hva.app.exceptions.VeterinarianNotAuthorizedException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoVaccinateAnimal extends Command<Hotel> {

    DoVaccinateAnimal(Hotel receiver) {
        super(Label.VACCINATE_ANIMAL, receiver);
        addStringField("vaccineId", Prompt.vaccineKey());
        addStringField("veterinarianId", Prompt.veterinarianKey());
        addStringField("animalId", hva.app.animal.Prompt.animalKey());
    }

    @Override
    protected final void execute() throws CommandException {
        String vaccineId = stringField("vaccineId");
        String veterinarianId = stringField("veterinarianId");
        String animalId = stringField("animalId");

        try {
            if (!(_receiver.vaccinateAnimal(vaccineId, veterinarianId, animalId))) {
                _display.popup(Message.wrongVaccine(vaccineId, animalId));
            }
        }
        catch (hva.exceptions.UnknownVaccineKeyException e) {
            throw new UnknownVaccineKeyException(e.getId());
        }
        catch (hva.exceptions.UnknownEmployeeKeyException e) {
            throw new UnknownEmployeeKeyException(e.getId());
        }
        catch (hva.exceptions.UnknownAnimalKeyException e) {
            throw new UnknownAnimalKeyException(e.getId());
        }
        catch (hva.exceptions.UnknownVeterinarianKeyException e) {
            throw new UnknownVeterinarianKeyException(e.getId());
        }
        catch (hva.exceptions.VeterinarianNotAuthorizedException e) {
            throw new VeterinarianNotAuthorizedException(e.getVeterinarianId(), e.getSpeciesId());
        }
    }

}
