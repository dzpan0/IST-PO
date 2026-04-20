package hva.app.search;

import hva.Hotel;
import hva.app.exceptions.UnknownHabitatKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowAnimalsInHabitat extends Command<Hotel> {

    DoShowAnimalsInHabitat(Hotel receiver) {
        super(Label.ANIMALS_IN_HABITAT, receiver);
        addStringField("habitatId", hva.app.habitat.Prompt.habitatKey());
    }

    @Override
    protected void execute() throws CommandException {
        String habitatId = stringField("habitatId");

        try {
            _receiver.getAllAnimalsInHabitat(habitatId).stream().
                    map(o -> o.toString()).forEach(_display::popup);
        }
        catch (hva.exceptions.UnknownHabitatKeyException e) {
            throw new UnknownHabitatKeyException(e.getId());
        }
    }

}
