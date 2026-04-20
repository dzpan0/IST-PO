package hva.employee;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import hva.exceptions.NoResponsibilityException;
import hva.calculator.VeterinarianSatisfactionCalculator;
import hva.species.Species;
import hva.vaccination.Vaccination;

public class Veterinarian extends Employee {

    @Serial
    private static final long serialVersionUID = 202410091832L;

    private Map<String, Species> _vaccinableSpecies;
    private List<Vaccination> _vaccinationHistory;

    public Veterinarian(String id, String name) {
        super(id, name);
        _vaccinableSpecies = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        _vaccinationHistory = new LinkedList<>();
        setSatisfactionCalculator(new VeterinarianSatisfactionCalculator(this));
    }

    public Species getSpecies(String id)
            throws NoResponsibilityException {
        return fetchSpecies(id);
    }

    private Species fetchSpecies(String id)
            throws NoResponsibilityException {
        Species species = _vaccinableSpecies.get(id);

        if (species == null) {
            throw new NoResponsibilityException(getId(), id);
        }

        return species;
    }

    public Collection<Species> getAllVaccinableSpecies() {
        return Collections.unmodifiableCollection(_vaccinableSpecies.values());
    }

    public Collection<Vaccination> getAllVaccinations() {
        return Collections.unmodifiableCollection(_vaccinationHistory);
    }

    public void addVaccinableSpecies(Species species) {
        _vaccinableSpecies.put(species.getId(), species);
    }

    public void removeVaccinableSpecies(String id) {
        _vaccinableSpecies.remove(id);
    }

    public void addVaccination(Vaccination vaccination) {
        _vaccinationHistory.add(vaccination);
    }

    @Override
    public String toString() {
        StringBuilder representation = new StringBuilder("VET|" +
                getId() + "|" + getName() + "|");

        for (String id : _vaccinableSpecies.keySet()) {
            representation.append(id).append(",");
        }

        return representation.
                deleteCharAt(representation.length() - 1).toString();
    }

}
