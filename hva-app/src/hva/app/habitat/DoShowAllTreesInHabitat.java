package hva.app.habitat;

import hva.Hotel;
import hva.app.exceptions.UnknownHabitatKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowAllTreesInHabitat extends Command<Hotel> {

    DoShowAllTreesInHabitat(Hotel receiver) {
        super(Label.SHOW_TREES_IN_HABITAT, receiver);
        addStringField("habitatId", Prompt.habitatKey());
    }

    @Override
    protected void execute() throws CommandException {
        String habitatId = stringField("habitatId");

        try {
            _receiver.getAllTreesInHabitat(habitatId).stream().
                    map(o -> o.toString()).forEach(_display::popup);
        }
        catch (hva.exceptions.UnknownHabitatKeyException e) {
            throw new UnknownHabitatKeyException(e.getId());
        }
    }

}
