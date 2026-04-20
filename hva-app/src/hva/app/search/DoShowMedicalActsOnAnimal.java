package hva.app.search;

import hva.Hotel;
import hva.app.exceptions.UnknownAnimalKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowMedicalActsOnAnimal extends Command<Hotel> {

    DoShowMedicalActsOnAnimal(Hotel receiver) {
        super(Label.MEDICAL_ACTS_ON_ANIMAL, receiver);
        addStringField("animalId", hva.app.animal.Prompt.animalKey());
    }

    @Override
    protected void execute() throws CommandException {
        String animalId = stringField("animalId");

        try {
            _receiver.getAllVaccinationsOnAnimal(animalId).stream().
                    map(o -> o.toString()).forEach(_display::popup);
        }
        catch (hva.exceptions.UnknownAnimalKeyException e) {
            throw new UnknownAnimalKeyException(e.getId());
        }
    }

}
