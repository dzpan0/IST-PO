package hva.app.employee;

import hva.Hotel;
import hva.app.exceptions.UnknownEmployeeKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowSatisfactionOfEmployee extends Command<Hotel> {

    DoShowSatisfactionOfEmployee(Hotel receiver) {
        super(Label.SHOW_SATISFACTION_OF_EMPLOYEE, receiver);
        addStringField("employeeId", Prompt.employeeKey());
    }

    @Override
    protected void execute() throws CommandException {
        String employeeId = stringField("employeeId");

        try {
            long satisfaction = Math.round(_receiver.
                    getEmployeeSatisfaction(employeeId));
            _display.popup(satisfaction);
        }
        catch (hva.exceptions.UnknownEmployeeKeyException e) {
            throw new UnknownEmployeeKeyException(e.getId());
        }
    }

}
