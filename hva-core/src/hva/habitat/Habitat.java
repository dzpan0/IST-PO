package hva.habitat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import hva.animal.Animal;
import hva.employee.Employee;
import hva.species.Species;
import hva.tree.Tree;

public class Habitat implements Serializable {

    @Serial
    private static final long serialVersionUID = 202410090738L;

    private final String _id;
    private String _name;
    private int _area;
    private Map<String, Tree> _trees;
    private Map<Species, String> _speciesInfluence;
    private Map<String, Animal> _animals;
    private Map<String, Employee> _managingEmployees;

    public Habitat(String id, String name, int area) {
        _id = id;
        _name = name;
        _area = area;
        _speciesInfluence = new HashMap<>();
        _trees = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        _animals = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        _managingEmployees = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    public String getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public int getArea() {
        return _area;
    }

    public void setArea(int area) {
        _area = area;
    }

    public Collection<Tree> getAllTrees() {
        return Collections.unmodifiableCollection(_trees.values());
    }

    public Collection<Animal> getAllAnimals() {
        return Collections.unmodifiableCollection(_animals.values());
    }

    public Collection<Employee> getAllManagingEmployees() {
        return Collections.unmodifiableCollection(_managingEmployees.values());
    }

    public String getInfluence(Species species) {
        String influence = _speciesInfluence.get(species);

        if (influence == null) {
            return "NEU";
        }

        return influence;
    }

    public void addTree(Tree tree) {
        _trees.put(tree.getId(), tree);
    }

    public void addAnimal(Animal animal) {
        _animals.put(animal.getId(), animal);
    }

    public void removeAnimal(String id) {
        _animals.remove(id);
    }

    public void addManagingEmployees(Employee employee) {
        _managingEmployees.put(employee.getId(), employee);
    }

    public void removeEmployee(String id) {
        _managingEmployees.remove(id);
    }

    public void changeInfluence(Species species, String influence) {
        _speciesInfluence.put(species, influence);
    }

    @Override
    public String toString() {
        StringBuilder representation = new StringBuilder("HABITAT|" +
                _id + "|" + _name + "|" + _area + "|" +
                _trees.size());

        for (Tree tree : _trees.values()) {
            representation.append("\n").append(tree.toString());
        }

        return  representation.toString();
    }

}
