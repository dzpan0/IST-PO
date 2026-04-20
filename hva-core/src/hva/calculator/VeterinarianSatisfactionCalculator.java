package hva.calculator;

import hva.employee.Veterinarian;
import hva.species.Species;

import java.io.Serial;
import java.io.Serializable;

public class VeterinarianSatisfactionCalculator implements Calculator, Serializable {

    @Serial
    private static final long serialVersionUID = 202410240326L;

    private Veterinarian _veterinarian;

    public VeterinarianSatisfactionCalculator(Veterinarian veterinarian) {
        _veterinarian = veterinarian;
    }

    private long getNumberOfAbleVeterinarians(Species species) {
        return species.getAllAbleEmployees().
                stream().filter(o -> o instanceof Veterinarian).count();
    }

    private double getWork() {
        double work = 0D;

        for (Species species : _veterinarian.getAllVaccinableSpecies()) {
            int speciesPopulation = species.getAllAnimals().size();
            work += (double) speciesPopulation / getNumberOfAbleVeterinarians(species);
        }

        return work;
    }

    @Override
    public double calculate() {
        return 20D - getWork();
    }

}
