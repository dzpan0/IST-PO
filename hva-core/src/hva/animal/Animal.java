package hva.animal;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;

import hva.calculator.AnimalSatisfactionCalculator;
import hva.calculator.Calculator;
import hva.habitat.Habitat;
import hva.species.Species;
import hva.vaccination.Vaccination;

public class Animal implements Serializable {

    @Serial
    private static final long serialVersionUID = 202410090638L;

    private final String _id;
    private String _name;
    private final Species _species;
    private Habitat _habitat;
    private List<Vaccination> _vaccinations;
    private Calculator _animalSatisfactionCalculator;

    public Animal(String id, String name, Species species, Habitat habitat) {
        _id = id;
        _name = name;
        _species = species;
        _habitat = habitat;
        _vaccinations = new LinkedList<>();
        _animalSatisfactionCalculator = new AnimalSatisfactionCalculator(this);
    }

    public String getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public Species getSpecie() {
        return _species;
    }

    public Habitat getHabitat() {
        return _habitat;
    }

    public void setHabitat(Habitat habitat) {
        _habitat = habitat;
    }

    public void setSatisfactionCalculator(Calculator calculator) {
        _animalSatisfactionCalculator = calculator;
    }

    public Collection<Vaccination> getAllVaccinations() {
        return Collections.unmodifiableCollection(_vaccinations);
    }

    public void addVaccinations(Vaccination vaccination) {
        _vaccinations.add(vaccination);
    }

    public double calculateSatisfaction() {
        return _animalSatisfactionCalculator.calculate();
    }

    public String getHealthHistory() {
        if (_vaccinations.isEmpty()) {
            return "VOID";
        }

        StringBuilder healthHistory = new StringBuilder();

        for (Vaccination vaccination : _vaccinations) {
            healthHistory.append(vaccination.getDamageTerm()).append(",");
        }

        return  healthHistory.
                deleteCharAt(healthHistory.length() - 1).toString();
    }

    @Override
    public String toString() {
        return "ANIMAL|" + _id + "|" + _name + "|" + _species.getId() +
                "|" + getHealthHistory() + "|" + _habitat.getId();
    }
}
