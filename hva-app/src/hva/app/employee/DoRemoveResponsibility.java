package hva.app.employee;

import hva.Hotel;
import hva.app.exceptions.NoResponsibilityException;
import hva.app.exceptions.UnknownEmployeeKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoRemoveResponsibility extends Command<Hotel> {

    DoRemoveResponsibility(Hotel receiver) {
        super(Label.REMOVE_RESPONSABILITY, receiver);
        addStringField("employeeId", Prompt.employeeKey());
        addStringField("responsibilityId", Prompt.responsibilityKey());
    }

    @Override
    protected void execute() throws CommandException {
        String employeeId = stringField("employeeId");
        String responsibilityId = stringField("responsibilityId");

        try {
            _receiver.removeResponsibilities(employeeId, responsibilityId);
        }
        catch (hva.exceptions.UnknownEmployeeKeyException e) {
            throw new UnknownEmployeeKeyException(e.getId());
        }
        catch (hva.exceptions.NoResponsibilityException e) {
            throw new NoResponsibilityException(e.getEmployeeId(), e.getResponsibilityId());
        }
    }

}
