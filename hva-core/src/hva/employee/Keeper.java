package hva.employee;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import hva.exceptions.NoResponsibilityException;
import hva.habitat.Habitat;
import hva.calculator.KeeperSatisfactionCalculator;
import hva.species.Species;

public class Keeper extends Employee {

    @Serial
    private static final long serialVersionUID = 202410091330L;

    private Map<String, Habitat> _managedHabitats;

    public Keeper(String id, String name) {
        super(id, name);
        _managedHabitats = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        setSatisfactionCalculator(new KeeperSatisfactionCalculator(this));
    }

    public Habitat getHabitat(String id)
            throws NoResponsibilityException {
        return fetchHabitat(id);
    }

    private Habitat fetchHabitat(String id)
            throws NoResponsibilityException {
        Habitat habitat = _managedHabitats.get(id);

        if (habitat == null) {
            throw new NoResponsibilityException(getId(), id);
        }

        return habitat;
    }

    public Collection<Habitat> getAllManagedHabitats() {
        return Collections.unmodifiableCollection(_managedHabitats.values());
    }

    public void addManagedHabitats(Habitat habitat) {
        _managedHabitats.put(habitat.getId(), habitat);
    }

    public void removeManagedHabitats(String id) {
        _managedHabitats.remove(id);
    }

    @Override
    public String toString() {
        StringBuilder representation = new StringBuilder("TRT|" +
                getId() + "|" + getName() + "|");

        for (String id : _managedHabitats.keySet()) {
            representation.append(id).append(",");
        }

        return representation.
                deleteCharAt(representation.length() - 1).toString();
    }

}
