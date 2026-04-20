package hva.vaccination;

import java.io.Serial;
import java.io.Serializable;

import hva.animal.Animal;
import hva.calculator.Calculator;
import hva.calculator.VaccinationDamageCalculator;
import hva.employee.Veterinarian;
import hva.vaccine.Vaccine;

public class Vaccination implements Serializable {

    @Serial
    private static final long serialVersionUID = 202410102209L;

    private Vaccine _usedVaccine;
    private Veterinarian _veterinarian;
    private Animal _appliedAnimal;
    private Calculator _damageCalculator;
    private int _damage;
    private String _damageTerm;

    public Vaccination(Vaccine vaccine, Veterinarian veterinarian, Animal animal) {
        _usedVaccine = vaccine;
        _veterinarian = veterinarian;
        _appliedAnimal = animal;
        _damageCalculator = new VaccinationDamageCalculator(this);
        _damage = -1;
        _damageTerm = "";
    }

    public Vaccine getUsedVaccine() {
        return _usedVaccine;
    }

    public Veterinarian getVeterinarian() {
        return _veterinarian;
    }

    public Animal getAppliedAnimal() {
        return _appliedAnimal;
    }

    public int getDamage() {
        if (_damage == -1) {
            if (!wasWrong()) {
                setDamage(0);
            }
            else {
                setDamage((int) _damageCalculator.calculate());
            }

            return _damage;
        }

        return _damage;
    }

    public String getDamageTerm() {
        if (!(_damageTerm.isEmpty())) {
            return _damageTerm;
        }

        int damage = getDamage();

        if (damage >= 5) {
            return "ERRO";
        }
        else if (damage >= 1) {
            return "ACIDENTE";
        }
        else if (!wasWrong()) {
            return "NORMAL";
        }
        else {
            return "CONFUSÃO";
        }
    }

    public void setDamage(int damage) {
        _damage = damage;
    }

    public void setDamageTerm(String term) {
        _damageTerm = term;
    }

    public boolean wasWrong() {
        return !(_usedVaccine.canApply(_appliedAnimal.getSpecie().getId()));
    }

    @Override
    public String toString() {
        return "REGISTO-VACINA|" + _usedVaccine.getId() + "|" +
                _veterinarian.getId() + "|" + _appliedAnimal.getSpecie().getId();
    }
}
