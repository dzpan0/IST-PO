package hva.app.animal;

import hva.Hotel;
import hva.app.exceptions.UnknownAnimalKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


class DoShowSatisfactionOfAnimal extends Command<Hotel> {

    DoShowSatisfactionOfAnimal(Hotel receiver) {
        super(Label.SHOW_SATISFACTION_OF_ANIMAL, receiver);
        addStringField("animalId", Prompt.animalKey());
    }

    @Override
    protected final void execute() throws CommandException {
        String animalId = stringField("animalId");

        try {
            long satisfaction = Math.round(_receiver.
                    getAnimalSatisfaction(animalId));
            _display.popup(satisfaction);
        }
        catch (hva.exceptions.UnknownAnimalKeyException e) {
            throw new UnknownAnimalKeyException(e.getId());
        }
    }

}
