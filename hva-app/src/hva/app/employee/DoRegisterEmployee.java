package hva.app.employee;

import hva.Hotel;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import hva.app.exceptions.DuplicateEmployeeKeyException;

class DoRegisterEmployee extends Command<Hotel> {

    DoRegisterEmployee(Hotel receiver) {
        super(Label.REGISTER_EMPLOYEE, receiver);
        addStringField("employeeId", Prompt.employeeKey());
        addStringField("employeeName", Prompt.employeeName());
        addOptionField("employeeType", Prompt.employeeType(), "TRT", "VET");
    }

    @Override
    protected void execute() throws CommandException {
        String employeeId = stringField("employeeId");
        String employeeName = stringField("employeeName");
        String employeeType = stringField("employeeType");

        try {
            _receiver.registerEmployee(employeeId, employeeName, employeeType);
        }
        catch (hva.exceptions.DuplicateEmployeeKeyException e) {
            throw new DuplicateEmployeeKeyException(e.getId());
        }
        catch (hva.exceptions.UnrecognizedEntryException e) {
            e.printStackTrace();
        }
    }

}
