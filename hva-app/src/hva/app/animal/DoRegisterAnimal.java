package hva.app.animal;

import hva.Hotel;
import hva.app.exceptions.DuplicateAnimalKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoRegisterAnimal extends Command<Hotel> {

    DoRegisterAnimal(Hotel receiver) {
        super(Label.REGISTER_ANIMAL, receiver);
        addStringField("animalId", Prompt.animalKey());
        addStringField("animalName", Prompt.animalName());
        addStringField("specieId", Prompt.speciesKey());
        addStringField("habitatId", hva.app.habitat.Prompt.habitatKey());
    }

    @Override
    protected final void execute() throws CommandException {
        String animalId = stringField("animalId");
        String animalName = stringField("animalName");
        String specieId = stringField("specieId");
        String habitatId = stringField("habitatId");

        try {
            try {
                _receiver.registerAnimal(animalId, animalName, specieId, habitatId);
            }
            catch (hva.exceptions.UnknownSpeciesKeyException e) {
                String speciesName = Form.requestString(Prompt.speciesName());

                try {
                    _receiver.registerSpecies(specieId, speciesName);
                }
                catch (hva.exceptions.DuplicateSpeciesKeyException |
                       hva.exceptions.DuplicateSpeciesNameException e1) {
                    e1.printStackTrace();
                }

                new DoRegisterAnimal(_receiver);
            }
        }
        catch (hva.exceptions.DuplicateAnimalKeyException e) {
            throw new DuplicateAnimalKeyException(e.getId());
        }
        catch (hva.exceptions.UnknownHabitatKeyException e) {
            throw new UnknownHabitatKeyException(e.getId());
        }
    }

}
