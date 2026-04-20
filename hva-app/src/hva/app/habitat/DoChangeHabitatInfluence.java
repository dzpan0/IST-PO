package hva.app.habitat;

import hva.Hotel;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.app.exceptions.UnknownSpeciesKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


class DoChangeHabitatInfluence extends Command<Hotel> {

    DoChangeHabitatInfluence(Hotel receiver) {
        super(Label.CHANGE_HABITAT_INFLUENCE, receiver);
        addStringField("habitatId", Prompt.habitatKey());
        addStringField("speciesId", hva.app.animal.Prompt.speciesKey());
        addOptionField("influence", Prompt.habitatInfluence(), "POS", "NEG", "NEU");
    }

    @Override
    protected void execute() throws CommandException {
        String habitatId = stringField("habitatId");
        String speciesId = stringField("speciesId");
        String influence = optionField("influence");

        try {
            _receiver.changeHabitatInfluence(habitatId, speciesId, influence);
        }
        catch (hva.exceptions.UnknownHabitatKeyException e) {
            throw new UnknownHabitatKeyException(e.getId());
        }
        catch (hva.exceptions.UnknownSpeciesKeyException e) {
            throw new UnknownSpeciesKeyException(e.getId());
        }
    }

}
