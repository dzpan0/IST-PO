package hva.calculator;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

import hva.animal.Animal;
import hva.habitat.Habitat;

public class AnimalSatisfactionCalculator implements Calculator, Serializable {

    @Serial
    private static final long serialVersionUID = 202410240326L;

    private Animal _animal;

    public AnimalSatisfactionCalculator(Animal animal) {
        _animal = animal;
    }

    private int getAdequacy(Habitat habitat) {
        return switch (habitat.getInfluence(_animal.getSpecie())) {
            case "NEG" -> -20;
            case "POS" -> 20;
            default -> 0;
        };
    }
    private long getNumberOfSameAnimalsInHabitat(Habitat habitat) {
        return habitat.getAllAnimals().stream().
                filter(o -> o.getSpecie().getId().equals(_animal.getSpecie().getId())).
                count();
    }

    @Override
    public double calculate() {
        Habitat habitat = _animal.getHabitat();
        Collection<Animal> animalsInHabitat = habitat.getAllAnimals();
        long numberOfSameAnimalsInHabitat = getNumberOfSameAnimalsInHabitat(habitat);

        int adequacy = getAdequacy(habitat);

        return (20D + 3 * (numberOfSameAnimalsInHabitat - 1) -
                2 * (animalsInHabitat.size() - numberOfSameAnimalsInHabitat) +
                (double) habitat.getArea() / animalsInHabitat.size() +
                adequacy);
    }

}
