package hva.calculator;

import java.io.Serial;
import java.io.Serializable;

import hva.employee.Keeper;
import hva.employee.Veterinarian;
import hva.habitat.Habitat;
import hva.species.Species;

public class KeeperSatisfactionCalculator implements Calculator, Serializable {

    @Serial
    private static final long serialVersionUID = 202410240326L;

    private Keeper _keeper;

    public KeeperSatisfactionCalculator(Keeper keeper) {
        _keeper = keeper;
    }

    private long getNumberOfManagingKeepers(Habitat habitat) {
        return habitat.getAllManagingEmployees().
                stream().filter(o -> o instanceof Keeper).count();
    }

    private double getWorkInHabitat(Habitat habitat) {
        double habitatWork = habitat.getAllTrees().stream().
                mapToDouble(o -> o.calculateCleaningEffort()).sum();

        return (habitat.getArea() + 3 * habitat.getAllAnimals().size() +
                habitatWork);
    }

    private double getWork() {
        double work = 0D;

        for (Habitat habitat : _keeper.getAllManagedHabitats()) {
            work += getWorkInHabitat(habitat) / getNumberOfManagingKeepers(habitat);
        }

        return work;
    }

    @Override
    public double calculate() {
        return 300D - getWork();
    }
}
