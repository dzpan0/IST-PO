package hva.app.employee;

import hva.Hotel;
import hva.app.exceptions.UnknownEmployeeKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.app.exceptions.UnknownSpeciesKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoAddResponsibility extends Command<Hotel> {

    DoAddResponsibility(Hotel receiver) {
        super(Label.ADD_RESPONSABILITY, receiver);
        addStringField("employeeId", Prompt.employeeKey());
        addStringField("responsibilityId", Prompt.responsibilityKey());
    }

    @Override
    protected void execute() throws CommandException {
        String employeeId = stringField("employeeId");
        String responsibilityId = stringField("responsibilityId");

        try {
            _receiver.addResponsibilities(employeeId, responsibilityId);
        }
        catch (hva.exceptions.UnknownEmployeeKeyException e) {
            throw new UnknownEmployeeKeyException(e.getId());
        }
        catch (hva.exceptions.UnknownHabitatKeyException e) {
            throw new UnknownHabitatKeyException(e.getId());
        }
        catch (hva.exceptions.UnknownSpeciesKeyException e) {
            throw new UnknownSpeciesKeyException(e.getId());
        }
    }

}
