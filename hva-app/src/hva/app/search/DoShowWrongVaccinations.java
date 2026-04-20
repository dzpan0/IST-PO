package hva.app.search;

import hva.Hotel;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowWrongVaccinations extends Command<Hotel> {

    DoShowWrongVaccinations(Hotel receiver) {
        super(Label.WRONG_VACCINATIONS, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        _receiver.getAllWrongVaccinations().stream().
                map(o -> o.toString()).forEach(_display::popup);
    }

}
