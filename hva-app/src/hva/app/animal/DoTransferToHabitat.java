package hva.app.animal;

import hva.Hotel;
import hva.app.exceptions.UnknownAnimalKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


class DoTransferToHabitat extends Command<Hotel> {

    DoTransferToHabitat(Hotel hotel) {
        super(Label.TRANSFER_ANIMAL_TO_HABITAT, hotel);
        addStringField("animalId", Prompt.animalKey());
        addStringField("habitatId", hva.app.habitat.Prompt.habitatKey());
    }

    @Override
    protected final void execute() throws CommandException {
        String animalId = stringField("animalId");
        String habitatId = stringField("habitatId");

        try {
            _receiver.transferToHabitat(animalId, habitatId);
        }
        catch (hva.exceptions.UnknownAnimalKeyException e) {
            throw new UnknownAnimalKeyException(e.getId());
        }
        catch (hva.exceptions.UnknownHabitatKeyException e) {
            throw new UnknownHabitatKeyException(e.getId());
        }
    }

}
