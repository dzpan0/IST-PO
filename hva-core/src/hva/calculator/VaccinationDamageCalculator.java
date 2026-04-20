package hva.calculator;

import hva.vaccination.Vaccination;
import hva.vaccine.Vaccine;

import java.io.Serial;
import java.io.Serializable;
import java.util.Comparator;

public class VaccinationDamageCalculator implements Calculator, Serializable {

    @Serial
    private static final long serialVersionUID = 202410241732L;

    private Vaccination _vaccination;

    public VaccinationDamageCalculator(Vaccination vaccination) {
        _vaccination = vaccination;
    }

    private String getLongestSpeciesName(Vaccine vaccine) {
        return vaccine.getAllApplicableSpecies().stream().
                map(o -> o.getName()).
                max(Comparator.comparingInt(String::length)).orElse("");
    }

    private int getNumberOfCommonLetters(String s1, String s2) {
        int initialLength = s2.length();
        StringBuilder stringBuilderS2 = new StringBuilder(s2.toLowerCase());

        for (char c : s1.toLowerCase().toCharArray()) {
            int index = stringBuilderS2.indexOf(String.valueOf(c));

            if (index != -1) {
                stringBuilderS2.deleteCharAt(index);
            }
        }

        return initialLength - stringBuilderS2.length();
    }

    @Override
    public double calculate() {
        String longest = getLongestSpeciesName(_vaccination.getUsedVaccine());
        int numberOfCommonLetters = getNumberOfCommonLetters(
                _vaccination.getAppliedAnimal().getSpecie().getName(), longest);

        return longest.length() - numberOfCommonLetters;
    }
}
