package hva.app.vaccine;

import hva.Hotel;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowVaccinations extends Command<Hotel> {

    DoShowVaccinations(Hotel receiver) {
        super(Label.SHOW_VACCINATIONS, receiver);
    }

    @Override
    protected final void execute() {
        _receiver.getAllVaccinations().stream().
                map(o -> o.toString()).forEach(_display::popup);
    }
}
