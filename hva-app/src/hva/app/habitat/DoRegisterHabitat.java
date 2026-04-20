package hva.app.habitat;

import hva.Hotel;
import hva.app.exceptions.DuplicateHabitatKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


class DoRegisterHabitat extends Command<Hotel> {

    DoRegisterHabitat(Hotel receiver) {
        super(Label.REGISTER_HABITAT, receiver);
        addStringField("habitatId", Prompt.habitatKey());
        addStringField("habitatName", Prompt.habitatName());
        addIntegerField("habitatArea", Prompt.habitatArea());
    }

    @Override
    protected void execute() throws CommandException {
        String habitatId = stringField("habitatId");
        String habitatName = stringField("habitatName");
        int habitatArea = integerField("habitatArea");

        try {
            _receiver.registerHabitat(habitatId, habitatName, habitatArea);
        }
        catch (hva.exceptions.DuplicateHabitatKeyException e) {
            throw new DuplicateHabitatKeyException(e.getId());
        }
        catch (hva.exceptions.NonPositiveIntegerException e) {
            e.printStackTrace();
        }
    }

}
