package hva.species;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

import hva.animal.Animal;
import hva.employee.Employee;

public class Species implements Serializable {

    @Serial
    private static final long serialVersionUID = 202410090650L;

    private final String _id;
    private String _name;
    private Map<String, Animal> _animals;
    private Map<String, Employee> _ableEmployees;

    public Species(String id, String name) {
        _id = id;
        _name = name;
        _animals = new HashMap<>();
        _ableEmployees = new HashMap<>();
    }

    public String getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public Collection<Animal> getAllAnimals() {
        return Collections.unmodifiableCollection(_animals.values());
    }

    public Collection<Employee> getAllAbleEmployees() {
        return Collections.unmodifiableCollection(_ableEmployees.values());
    }

    public void addAbleEmployee(Employee employee) {
        _ableEmployees.put(employee.getId(), employee);
    }

    public void removeAbleEmployee(String id) {
        _ableEmployees.remove(id);
    }

    public void addAnimal(Animal animal) {
        _animals.put(animal.getId(), animal);
    }

}
