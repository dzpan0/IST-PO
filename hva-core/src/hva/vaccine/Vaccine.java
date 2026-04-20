package hva.vaccine;

import hva.species.Species;
import hva.vaccination.Vaccination;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Vaccine implements Serializable {

    @Serial
    private static final long serialVersionUID = 202410090830L;

    private final String _id;
    private String _name;
    private Map<String, Species> _applicableSpecies;
    private List<Vaccination> _vaccinations;


    public Vaccine(String id, String name) {
        _id = id;
        _name = name;
        _applicableSpecies = new TreeMap<>();
        _vaccinations = new LinkedList<>();
    }

    public String getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public Collection<Species> getAllApplicableSpecies() {
        return Collections.unmodifiableCollection(_applicableSpecies.values());
    }

    public void addApplicableSpecie(Species species) {
        _applicableSpecies.put(species.getId(), species);
    }

    public void addVaccination(Vaccination vaccination) {
        _vaccinations.add(vaccination);
    }

    public boolean canApply(String SpeciesId) {
        return _applicableSpecies.get(SpeciesId) != null;
    }

    @Override
    public String toString() {
        StringBuilder representation = new StringBuilder("VACINA|" +
                _id + "|" + _name + "|" + _vaccinations.size() + "|");

        for (Species species : _applicableSpecies.values()) {
            representation.append(species.getId()).append(",");
        }

        return representation.
                deleteCharAt(representation.length() - 1).toString();
    }

}
