package hva.app.habitat;

import hva.Hotel;
import hva.app.exceptions.DuplicateTreeKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoAddTreeToHabitat extends Command<Hotel> {

    DoAddTreeToHabitat(Hotel receiver) {
        super(Label.ADD_TREE_TO_HABITAT, receiver);
        addStringField("habitatId", Prompt.habitatKey());
        addStringField("treeId", Prompt.treeKey());
        addStringField("treeName", Prompt.treeName());
        addIntegerField("treeAge", Prompt.treeAge());
        addIntegerField("cleaningDifficulty", Prompt.treeDifficulty());
        addOptionField("treeType", Prompt.treeType(), "PERENE", "CADUCA");
    }

    @Override
    protected void execute() throws CommandException {
        String habitatId = stringField("habitatId");
        String treeId = stringField("treeId");
        String treeName = stringField("treeName");
        int treeAge = integerField("treeAge");
        int cleaningDifficulty = integerField("cleaningDifficulty");
        String treeType = optionField("treeType");

        try {
            _display.popup(_receiver.plantTreeInHabitat(habitatId,
                    treeId, treeName, treeAge, cleaningDifficulty,
                    treeType).toString());
        }
        catch (hva.exceptions.UnknownHabitatKeyException e) {
            throw new UnknownHabitatKeyException(e.getId());
        }
        catch (hva.exceptions.DuplicateTreeKeyException e) {
            throw new DuplicateTreeKeyException(e.getId());
        }
        catch (hva.exceptions.UnrecognizedEntryException e) {
            e.printStackTrace();
        }
        catch (hva.exceptions.NonPositiveIntegerException e) {
            e.printStackTrace();
        }
    }

}
