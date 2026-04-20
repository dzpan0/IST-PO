package hva;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serial;
import java.io.Serializable;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import hva.animal.Animal;
import hva.employee.Employee;
import hva.employee.Keeper;
import hva.employee.Veterinarian;
import hva.exceptions.DuplicateAnimalKeyException;
import hva.exceptions.DuplicateEmployeeKeyException;
import hva.exceptions.DuplicateHabitatKeyException;
import hva.exceptions.DuplicateSpeciesKeyException;
import hva.exceptions.DuplicateSpeciesNameException;
import hva.exceptions.DuplicateTreeKeyException;
import hva.exceptions.DuplicateVaccineKeyException;
import hva.exceptions.ImportFileException;
import hva.exceptions.NonPositiveIntegerException;
import hva.exceptions.NoResponsibilityException;
import hva.exceptions.UnknownAnimalKeyException;
import hva.exceptions.UnknownEmployeeKeyException;
import hva.exceptions.UnknownHabitatKeyException;
import hva.exceptions.UnknownTreeKeyException;
import hva.exceptions.UnknownSpeciesKeyException;
import hva.exceptions.UnknownVaccineKeyException;
import hva.exceptions.UnknownVeterinarianKeyException;
import hva.exceptions.UnrecognizedEntryException;
import hva.exceptions.VeterinarianNotAuthorizedException;
import hva.habitat.Habitat;
import hva.species.Species;
import hva.season.Season;
import hva.tree.DeciduousTree;
import hva.tree.EvergreenTree;
import hva.tree.Tree;
import hva.vaccination.Vaccination;
import hva.vaccine.Vaccine;

public class Hotel implements Serializable {

    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    private Season _season = Season.SPRING;

    /** Stores all the hotel's animals, sorted by their id. */
    private Map<String, Animal> _animals =
            new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** Stores all the hotel's species, sorted by their id. */
    private Map<String, Species> _species =
            new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** Stores all the hotel's employees, sorted by their id. */
    private Map<String, Employee> _employees =
            new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** Stores all the hotel's habitats, sorted by their id. */
    private Map<String, Habitat> _habitats =
            new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** Stores all the hotel's trees. */
    private Map<String, Tree> _trees =
            new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** Stores all the hotel's vaccines, sorted by their id. */
    private Map<String, Vaccine> _vaccines =
            new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** Stores all the hotel's vaccinations, sorted by their applied time. */
    private List<Vaccination> _vaccinations = new LinkedList<>();

    /** Whether the hotel has changed since the last time it was saved or created. */
    private boolean _changed = false;

    /** Stores the current entry that's being parsed when importing a file. */
    private String _currentEntry;

    /**
     * States whether the hotel has changed since
     * the last time it was saved or created.
     * 
     * @return the value of the changed flag.
     */
    public boolean hasChanged() {
        return _changed;
    }

    /**
     * Sets the changed flag to the value it receives.
     *
     * @param changed the value which the changed flag modifies to
     */
    public void setChanged(boolean changed) {
        _changed = changed;
    }

    /**
     * Enables the changed flag, indicating that the
     * hotel has changed since the last save or creation.
     */
    public void changed() {
        setChanged(true);
    }

    /**
     * Sets the current entry to the entry that's being parsed.
     *
     * @param entry the entry getting parsed
     */
    private void setCurrentEntry(String entry) {
        _currentEntry = entry;
    }

    /**
     * Gets all the species in the hotel.
     *
     * @return a {@link Collection} of the species,
     *         sorted by their id (case-insensitive)
     */
    public Collection<Species> getAllSpecies() {
        return Collections.unmodifiableCollection(_species.values());
    }

    /**
     * Gets all the animals in the hotel.
     *
     * @return a {@link Collection} of the animals,
     *         sorted by their id (case-insensitive)
     */
    public Collection<Animal> getAllAnimals() {
        return Collections.unmodifiableCollection(_animals.values());
    }

    /**
     * Gets all the employees in the hotel.
     *
     * @return a {@link Collection} of the employees,
     *         sorted by their id (case-insensitive)
     */
    public Collection<Employee> getAllEmployees() {
        return Collections.unmodifiableCollection(_employees.values());
    }

    /**
     * Gets all the habitats in the hotel.
     *
     * @return a {@link Collection} of the habitats,
     *         sorted by their id (case-insensitive)
     */
    public Collection<Habitat> getAllHabitats() {
        return Collections.unmodifiableCollection(_habitats.values());
    }

    /**
     * Gets all the vaccines in the hotel.
     *
     * @return a {@link Collection} of the vaccines,
     *         sorted by their id (case-insensitive)
     */
    public Collection<Vaccine> getAllVaccines() {
        return Collections.unmodifiableCollection(_vaccines.values());
    }

    /**
     * Gets all the vaccinations in the hotel.
     *
     * @return a {@link Collection} of the vaccinations,
     *         sorted by their applied time
     */
    public Collection<Vaccination> getAllVaccinations() {
        return Collections.unmodifiableCollection(_vaccinations);
    }

    /**
     * Gets all the trees in the given habitat.
     *
     * @param id id of the habitat
     * @return a {@link Collection} of the trees in the habitat,
     *         sorted by the tree's id (case-insensitive)
     * @throws UnknownHabitatKeyException if the habitat with given id
     *                                    doesn't exist in the hotel
     */
    public Collection<Tree> getAllTreesInHabitat(String id)
            throws UnknownHabitatKeyException {
        Habitat habitat = getHabitat(id);
        return habitat.getAllTrees();
    }

    /**
     * Gets all the animals in the given habitat.
     *
     * @param id the id of the habitat
     * @return a {@link Collection} of the animals in the habitat,
     *         sorted by the animal's id (case-insensitive)
     * @throws UnknownHabitatKeyException if the habitat with given id
     *                                    doesn't exist in the hotel
     */
    public Collection<Animal> getAllAnimalsInHabitat(String id)
            throws UnknownHabitatKeyException {
        Habitat habitat = getHabitat(id);
        return habitat.getAllAnimals();
    }

    /**
     * Gets all the vaccinations applied to an animal.
     *
     * @param id the id of the animal
     * @return a {@link Collection} of the vaccinations on an animal,
     *         sorted by their applied time
     * @throws UnknownAnimalKeyException if the animal with given id
     *                                   doesn't exist in the hotel
     */
    public Collection<Vaccination> getAllVaccinationsOnAnimal(String id)
            throws UnknownAnimalKeyException {
        Animal animal = getAnimal(id);
        return animal.getAllVaccinations();
    }

    /**
     * Gets all the vaccinations applied by a veterinarian.
     *
     * @param id the id of the veterinarian
     * @return a {@link Collection} of the vaccinations applied
     *         by a veterinarian, sorted by their applied time
     * @throws UnknownVeterinarianKeyException if the employee with given id
     *                                         doesn't exist in the hotel or
     *                                         is not a veterinarian
     */
    public Collection<Vaccination> getAllVaccinationsByVeterinarian(String id)
            throws UnknownVeterinarianKeyException{
        try {
            Employee employee = getEmployee(id);
            assertVeterinarian(employee);
            return ((Veterinarian) employee).getAllVaccinations();
        }
        catch (UnknownEmployeeKeyException e) {
            throw new UnknownVeterinarianKeyException(id);
        }
    }

    /**
     * Gets all the vaccinations that were wrongly applied.
     *
     * @return a {@link Collection} of wrongly applied vaccinations,
     *         sorted by their applied time
     */
    public Collection<Vaccination> getAllWrongVaccinations() {
        return Collections.unmodifiableCollection(getAllVaccinations().
                stream().filter(o -> o.wasWrong()).
                collect(Collectors.toList()));
    }

    /**
     * Gets a species using its id.
     *
     * @param id the id of the species to get
     * @return the {@link Species} wanted
     * @throws UnknownSpeciesKeyException if the species with given id
     *                                    doesn't exist in the hotel
     */
    public Species getSpecie(String id)
            throws  UnknownSpeciesKeyException {
        return fetchSpecie(id);
    }

    /**
     * Checks if a species can be fetched and retrieves it using its id.
     *
     * @param id the id of the species to be fetched
     * @return the {@link Species} fetched with the given id
     * @throws UnknownSpeciesKeyException if the species with given id
     *                                    doesn't exist in the hotel
     */
    private Species fetchSpecie(String id)
            throws UnknownSpeciesKeyException {
        Species species = _species.get(id);

        if (species == null) {
            throw new UnknownSpeciesKeyException(id);
        }

        return species;
    }

    /**
     * Gets an animal using its id.
     *
     * @param id the id of the animal to be fetched
     * @return the {@link Animal} wanted
     * @throws UnknownAnimalKeyException if the animal with given id
     *                                   doesn't exist in the hotel
     */
    public Animal getAnimal(String id)
            throws UnknownAnimalKeyException {
        return fetchAnimal(id);
    }

    /**
     * Checks if an animal can be fetched and retrieves it using its id.
     *
     * @param id the id of the animal to be fetched
     * @return the {@link Animal} fetched with the given id
     * @throws UnknownAnimalKeyException if the animal with given id
     *                                   doesn't exist in the hotel
     */
    private Animal fetchAnimal(String id)
            throws UnknownAnimalKeyException {
        Animal animal = _animals.get(id);

        if (animal == null) {
            throw new UnknownAnimalKeyException(id);
        }

        return animal;
    }

    /**
     * Gets a tree using its id.
     *
     * @param id the id of the employee to get
     * @return the {@link Employee} wanted
     * @throws UnknownEmployeeKeyException if the employee with given id
     *                                     doesn't exist in the hotel
     */
    public Employee getEmployee(String id)
            throws UnknownEmployeeKeyException {
        return fetchEmployee(id);
    }

    /**
     * Checks if an employee can be fetched and retrieves it using its id.
     *
     * @param id the id of the employee to be fetched
     * @return the {@link Employee} fetched with the given id
     * @throws UnknownEmployeeKeyException if the employee with given id
     *                                     doesn't exist in the hotel
     */
    private Employee fetchEmployee(String id)
            throws UnknownEmployeeKeyException {
        Employee employee = _employees.get(id);

        if (employee == null) {
            throw new UnknownEmployeeKeyException(id);
        }

        return employee;
    }

    /**
     * Gets a habitat using its id.
     *
     * @param id the id of the habitat to get
     * @return the {@link Habitat} wanted
     * @throws UnknownHabitatKeyException if the habitat with given id
     *                                    doesn't exist in the hotel
     */
    public Habitat getHabitat(String id)
            throws UnknownHabitatKeyException {
        return fetchHabitat(id);
    }

    /**
     * Checks if a habitat can be fetched and retrieves it using its id.
     *
     * @param id the id of the habitat to be fetched
     * @return the {@link Habitat} fetched with the given id
     * @throws UnknownHabitatKeyException if the habitat with given id
     *                                    doesn't exist in the hotel
     */
    private Habitat fetchHabitat(String id)
            throws UnknownHabitatKeyException {
        Habitat habitat = _habitats.get(id);

        if (habitat == null) {
            throw new UnknownHabitatKeyException(id);
        }

        return habitat;
    }

    /**
     * Gets a tree using its id.
     *
     * @param id the id of the tree to get
     * @return the {@link Tree} wanted
     * @throws UnknownTreeKeyException if the tree with given id
     *                                 doesn't exist in the hotel
     */
    public Tree getTree(String id)
            throws UnknownTreeKeyException {
        return fetchTree(id);
    }

    /**
     * Checks if a tree can be fetched and retrieves it using its id.
     *
     * @param id the id of the tree to be fetched
     * @return the {@link Tree} fetched with the given id
     * @throws UnknownTreeKeyException if the tree with given id
     *                                 doesn't exist in the hotel
     */
    private Tree fetchTree(String id)
            throws UnknownTreeKeyException {
        Tree tree = _trees.get(id);

        if (tree == null) {
            throw new UnknownTreeKeyException(id);
        }

        return tree;
    }

    /**
     * Gets a vaccine using its id.
     *
     * @param id the id of the vaccine to get
     * @return the {@link Vaccine} wanted
     * @throws UnknownVaccineKeyException if the vaccine with given id
     *                                    doesn't exist in the hotel
     */
    public Vaccine getVaccine(String id)
            throws UnknownVaccineKeyException {
        return fetchVaccine(id);
    }

    /**
     * Checks if a vaccine can be fetched and retrieves it using its id.
     *
     * @param id the id of the vaccine to be fetched
     * @return the {@link Vaccine} fetched with the given id
     * @throws UnknownVaccineKeyException if the vaccine with given id
     *                                    doesn't exist in the hotel
     */
    private Vaccine fetchVaccine(String id)
            throws UnknownVaccineKeyException {
        Vaccine vaccine = _vaccines.get(id);

        if (vaccine == null) {
            throw new UnknownVaccineKeyException(id);
        }

        return vaccine;
    }

    /**
     * Registers a new species into the hotel.
     *
     * @param id the identifier of the species
     * @param name the name of the species
     * @throws DuplicateSpeciesKeyException  if the given species id (case-insensitive)
     *                                       already exists in the hotel
     * @throws DuplicateSpeciesNameException if the given species name
     *                                       already exists in the hotel
     */
    public Species registerSpecies(String id, String name)
            throws DuplicateSpeciesKeyException, DuplicateSpeciesNameException {
        assertNewSpecies(id, name);
        Species species = new Species(id, name);
        _species.put(id, species);
        changed();
        return species;
    }

    /**
     * Registers a new animal into the hotel.
     *
     * @param animalId   the identifier of the animal
     * @param animalName the name of the animal
     * @param speciesId   the identifier of the species to which the animal belongs to
     * @param habitatId  the identifier of the habitat that homes the animal
     * @return the {@link Animal} that got created
     * @throws DuplicateAnimalKeyException if the given animal id (case-insensitive)
     *                                     already exists in the hotel
     * @throws UnknownSpeciesKeyException  if the species with given id
     *                                     doesn't exist in the hotel
     * @throws UnknownHabitatKeyException  if the habitat with given id
     *                                     doesn't exist in the hotel
     */
    public Animal registerAnimal(String animalId, String animalName, String speciesId, String habitatId)
            throws DuplicateAnimalKeyException, UnknownSpeciesKeyException,
            UnknownHabitatKeyException {
        assertNewAnimal(animalId);
        Species species = getSpecie(speciesId);
        Habitat habitat = getHabitat(habitatId);
        Animal animal = new Animal(animalId, animalName, species, habitat);
        _animals.put(animalId, animal);
        species.addAnimal(animal);
        habitat.addAnimal(animal);
        changed();
        return animal;
    }

    /**
     * Registers a new employee into the hotel.
     *
     * @param id   the identifier of the employee
     * @param name the name of the employee
     * @param type the employee type, which can be either
     *             a keeper (TRT) or veterinarian (VET)
     * @return the {@link Employee} that got created
     * @throws DuplicateEmployeeKeyException if the given employee id (case-insensitive)
     *                                       already exists in the hotel
     * @throws UnrecognizedEntryException    if the given employee type isn't either
     *                                       TRT or VET
     */
    public Employee registerEmployee(String id, String name, String type)
            throws DuplicateEmployeeKeyException, UnrecognizedEntryException {
        assertNewEmployee(id);

        Employee employee = switch (type) {
            case "TRT" -> new Keeper(id, name);
            case "VET" -> new Veterinarian(id, name);
            default -> throw new UnrecognizedEntryException(type);
        };

        _employees.put(id, employee);
        changed();
        return employee;
    }

    /**
     * Registers a new habitat into the hotel.
     *
     * @param id   the identifier of the habitat
     * @param name the name of the habitat
     * @param area the area of the habitat
     * @return the {@link Habitat} that got created
     * @throws DuplicateHabitatKeyException if the given habitat id (case-insensitive)
     *                                      already exists in the hotel
     * @throws NonPositiveIntegerException  if the given area isn't a positive value
     */
    public Habitat registerHabitat(String id, String name, int area)
            throws DuplicateHabitatKeyException, NonPositiveIntegerException {
        assertNewHabitat(id);
        assertPositiveInteger(area);;
        Habitat habitat = new Habitat(id, name, area);
        _habitats.put(id, habitat);
        changed();
        return habitat;
    }

    /**
     * Registers a new tree into the hotel.
     *
     * @param id                 the identifier of the tree
     * @param name               the name of the tree
     * @param cleaningDifficulty the cleaning difficulty of the tree
     * @param age                the age of the tree
     * @param type               the tree type, which can be either a
     *                           deciduous (CADUCA) or evergreen (PERENE)
     * @return the {@link Tree} that got created
     * @throws DuplicateTreeKeyException  if the given tree id (case-insensitive)
     *                                    already exists in the hotel
     * @throws UnrecognizedEntryException if the given tree type isn't either
     *                                    CADUCA or PERENE
     */
    public Tree registerTree(String id, String name, int cleaningDifficulty, int age, String type)
            throws DuplicateTreeKeyException, UnrecognizedEntryException,
            NonPositiveIntegerException{
        assertNewTree(id);
        assertPositiveInteger(cleaningDifficulty);

        Tree tree = switch (type) {
            case "CADUCA" -> new DeciduousTree(id, name, cleaningDifficulty, age, _season);
            case "PERENE" -> new EvergreenTree(id, name, cleaningDifficulty, age, _season);
            default -> throw new UnrecognizedEntryException(type);
        };

        _trees.put(id, tree);
        changed();
        return tree;
    }

    /**
     * Registers a new vaccine with its applicable species into the hotel.
     *
     * @param vaccineId   the identifier of the vaccine
     * @param vaccineName the name of the vaccine
     * @param specieIds   an array of identifiers of the species
     *                    that the vaccine can apply
     * @return the {@link Vaccine} that got created
     * @throws DuplicateVaccineKeyException if the given vaccine id (case-insensitive)
     *                                      already exists in the hotel
     * @throws UnknownSpeciesKeyException   if the species with given id
     *                                      doesn't exist in the hotel
     */
    public Vaccine registerVaccine(String vaccineId, String vaccineName, String specieIds)
            throws DuplicateVaccineKeyException, UnknownSpeciesKeyException {
        assertNewVaccine(vaccineId);
        Vaccine vaccine = new Vaccine(vaccineId, vaccineName);

        for (String speciesId : specieIds.split("\\s*,\\s*")) {
            vaccine.addApplicableSpecie(getSpecie(speciesId));
        }

        _vaccines.put(vaccineId, vaccine);
        changed();
        return vaccine;
    }

    /**
     * Registers a new vaccine into the hotel.
     *
     * @param vaccineId   the identifier of the vaccine
     * @param vaccineName the name of the vaccine
     * @return the {@link Vaccine} that got created
     * @throws DuplicateVaccineKeyException if the given vaccine id (case-insensitive)
     *                                      already exists in the hotel
     * @throws UnknownSpeciesKeyException   if the species with given id
     *                                      doesn't exist in the hotel
     */
    public Vaccine registerVaccine(String vaccineId, String vaccineName)
            throws DuplicateVaccineKeyException, UnknownSpeciesKeyException {
        assertNewVaccine(vaccineId);
        Vaccine vaccine = new Vaccine(vaccineId, vaccineName);
        _vaccines.put(vaccineId, vaccine);
        changed();
        return vaccine;
    }

    private Vaccination registerVaccination(String vaccineId, String veterinarianId, String animalId)
            throws UnknownVaccineKeyException, UnknownEmployeeKeyException,
            UnknownAnimalKeyException, UnknownVeterinarianKeyException,
            VeterinarianNotAuthorizedException {
        Vaccine vaccine = getVaccine(vaccineId);
        Employee employee = getEmployee(veterinarianId);
        Animal animal = getAnimal(animalId);
        assertVeterinarian(employee);
        Veterinarian veterinarian = (Veterinarian) employee;
        Species species = animal.getSpecie();

        try {
            veterinarian.getSpecies(species.getId());
        }
        catch (NoResponsibilityException e) {
            throw new VeterinarianNotAuthorizedException(veterinarianId, species.getId());
        }

        Vaccination vaccination = new Vaccination(vaccine, veterinarian, animal);
        vaccine.addVaccination(vaccination);
        veterinarian.addVaccination(vaccination);
        animal.addVaccinations(vaccination);
        _vaccinations.add(vaccination);
        changed();
        return vaccination;
    }

    /**
     * Checks if a new species can be registered
     * (the species id and name given cannot already be in the hotel).
     *
     * @param id   the id of the species to be checked
     * @param name the name of the species to be checked
     * @throws DuplicateSpeciesKeyException  if the given species id (case-insensitive)
     *                                       already exists in the hotel
     * @throws DuplicateSpeciesNameException if the given species name
     *                                       already exists in the hotel
     */
    private void assertNewSpecies(String id, String name)
            throws DuplicateSpeciesKeyException, DuplicateSpeciesNameException {
        if (_species.containsKey(id)) {
            throw new DuplicateSpeciesKeyException(id);
        }
        else if (getAllSpecies().stream().anyMatch(o -> o.getName().equals(name))) {
            throw new DuplicateSpeciesNameException(name);
        }
    }

    /**
     * Checks if a new animal can be registered
     * (the animal id given cannot already be in the hotel).
     *
     * @param id the id of the animal to be checked
     * @throws DuplicateAnimalKeyException if the given animal id (case-insensitive)
     *                                     already exists in the hotel
     */
    private void assertNewAnimal(String id)
            throws DuplicateAnimalKeyException{
        if (_animals.containsKey(id)) {
            throw new DuplicateAnimalKeyException(id);
        }
    }

    /**
     * Checks if a new employee can be registered
     * (the employee id given cannot already be in the hotel).
     *
     * @param id the id of the employee to be checked
     * @throws DuplicateEmployeeKeyException if the given employee id (case-insensitive)
     *                                       already exists in the hotel
     */
    private void assertNewEmployee(String id)
            throws DuplicateEmployeeKeyException {
        if (_employees.containsKey(id)){
            throw new DuplicateEmployeeKeyException(id);
        }
    }

    /**
     * Checks if a new habitat can be registered
     * (the habitat id given cannot already be in the hotel).
     *
     * @param id the id of the habitat to be checked
     * @throws DuplicateHabitatKeyException if the given habitat id (case-insensitive)
     *                                      already exists in the hotel
     */
    private void assertNewHabitat(String id)
            throws DuplicateHabitatKeyException {
        if (_habitats.containsKey(id)) {
            throw new DuplicateHabitatKeyException(id);
        }
    }

    /**
     * Checks if a new tree can be registered.
     *
     * @param id the id of the tree
     * @throws DuplicateTreeKeyException if the given tree id (case-insensitive)
     *                                   already exists in the hotel
     */
    private void assertNewTree(String id)
            throws DuplicateTreeKeyException {
        if (_trees.containsKey(id)) {
            throw new DuplicateTreeKeyException(id);
        }
    }

    /**
     * Checks if a new vaccine can be registered
     * (the given vaccine key cannot already be in the hotel).
     *
     * @param id the id of the vaccine to be checked
     * @throws DuplicateVaccineKeyException if the given vaccine id (case-insensitive)
     *                                      already exists in the hotel
     */
    private void assertNewVaccine(String id)
            throws DuplicateVaccineKeyException {
        if (_vaccines.containsKey(id)) {
            throw new DuplicateVaccineKeyException(id);
        }
    }

    /**
     * Checks if the habitat's integer is valid.
     *
     * @param integer the integer to be checked
     * @throws NonPositiveIntegerException if the integer given isn't a positive value
     */
    private void assertPositiveInteger(int integer)
            throws NonPositiveIntegerException {
        if (integer <= 0) {
            throw new NonPositiveIntegerException(integer);
        }
    }

    /**
     * Checks if an employee is a veterinarian.
     *
     * @param employee the employee to be checked
     * @throws UnknownVeterinarianKeyException if the given employee
     *                                         is not a veterinarian
     */
    private void assertVeterinarian(Employee employee)
            throws UnknownVeterinarianKeyException{
        if (!(employee instanceof Veterinarian)) {
            throw new UnknownVeterinarianKeyException(employee.getId());
        }
    }

    /**
     * Advances the season, affecting all the trees of every habitat.
     *
     * @return code of the changed season
     */
    public int advanceSeason() {
        _season = _season.getNextSeason();

        for (Tree tree : _trees.values()) {
            tree.advanceSeason();
        }

        changed();
        return _season.getCode();
    }

    public double getGlobalAnimalSatisfaction() {
        return _animals.values().stream().
                mapToDouble(a -> a.calculateSatisfaction()).sum();
    }

    public double getGlobalEmployeeSatisfaction() {
        return _employees.values().stream().
                mapToDouble(e -> e.calculateSatisfaction()).sum();
    }

    public double getGlobalSatisfaction() {
        return getGlobalAnimalSatisfaction() + getGlobalEmployeeSatisfaction();
    }

    /**
     * Transfers an animal to another habitat.
     *
     * @param animalId  the id of the animal to be transferred
     * @param habitatId the id of the destination habitat
     * @throws UnknownAnimalKeyException  if the animal with given id
     *                                    doesn't exist in the hotel
     * @throws UnknownHabitatKeyException if the habitat with given id
     *                                    doesn't exist in the hotel
     */
    public void transferToHabitat(String animalId, String habitatId)
            throws UnknownAnimalKeyException, UnknownHabitatKeyException {
        Animal animal = getAnimal(animalId);
        Habitat habitat = getHabitat(habitatId);
        animal.getHabitat().removeAnimal(animalId);
        animal.setHabitat(habitat);
        habitat.addAnimal(animal);
        changed();
    }

    /**
     * Gets the satisfaction of the given animal.
     *
     * @param id id of the animal
     * @return the satisfaction of the animal
     * @throws UnknownAnimalKeyException if the animal with given id
     *                                   doesn't exist in the hotel
     */
    public double getAnimalSatisfaction(String id)
            throws UnknownAnimalKeyException {
        Animal animal = getAnimal(id);
        return animal.calculateSatisfaction();
    }

    /**
     * Adds the responsibilities of an employee to the given employee.
     *
     * @param employeeId        the id of the employee
     * @param responsibilityIds the id of the responsibilities to be added
     * @throws UnknownEmployeeKeyException if the employee with given id
     *                                     doesn't exist in the hotel
     * @throws UnknownHabitatKeyException  if the habitat with given id
     *                                     doesn't exist in the hotel
     * @throws UnknownSpeciesKeyException  if the species with given id
     *                                     doesn't exist in the hotel
     */
    public void addResponsibilities(String employeeId, String... responsibilityIds)
            throws UnknownEmployeeKeyException, UnknownHabitatKeyException,
            UnknownSpeciesKeyException {
        Employee employee = getEmployee(employeeId);

        if (employee instanceof Keeper) {
            for (String id : responsibilityIds) {
                Habitat habitat = getHabitat(id);
                ((Keeper) employee).addManagedHabitats(getHabitat(id));
                habitat.addManagingEmployees(employee);
            }
        }
        else {
            for (String id : responsibilityIds) {
                Species species = getSpecie(id);
                ((Veterinarian) employee).addVaccinableSpecies(species);
                species.addAbleEmployee(employee);
            }
        }

        changed();
    }

    /**
     * Removes a responsibility of an employee.
     *
     * @param employeeId        the id of the employee
     * @param responsibilityId the id of the responsibilities to be removed
     * @throws UnknownEmployeeKeyException if the employee with given id
     *                                     doesn't exist in the hotel
     * @throws NoResponsibilityException   if the responsibility with given id
     *                                     doesn't exist in the hotel or
     *                                     the employee doesn't have the responsibility
     */
    public void removeResponsibilities(String employeeId, String responsibilityId)
            throws UnknownEmployeeKeyException, NoResponsibilityException {
        Employee employee = getEmployee(employeeId);

        try {
            if (employee instanceof Keeper) {
                Habitat habitat = getHabitat(responsibilityId);
                ((Keeper) employee).getHabitat(responsibilityId);
                ((Keeper) employee).removeManagedHabitats(responsibilityId);
                habitat.removeEmployee(employeeId);
            }
            else {
                Species species = getSpecie(responsibilityId);
                ((Veterinarian) employee).getSpecies(responsibilityId);
                ((Veterinarian) employee).removeVaccinableSpecies(responsibilityId);
                species.removeAbleEmployee(employeeId);
            }
        }
        catch (UnknownHabitatKeyException | UnknownSpeciesKeyException e) {
            throw new NoResponsibilityException(employeeId, responsibilityId);
        }

        changed();
    }

    /**
     * Gets the satisfaction of the given employee.
     *
     * @param id id of the employee
     * @return the satisfaction of the employee
     * @throws UnknownEmployeeKeyException if the employee with given id
     *                                   doesn't exist in the hotel
     */
    public double getEmployeeSatisfaction(String id)
            throws UnknownEmployeeKeyException {
        Employee employee = getEmployee(id);
        return employee.calculateSatisfaction();
    }

    /**
     * Changes the area of the given habitat.
     * @param id   the id of the habitat
     * @param area the area to be changed to
     * @throws UnknownHabitatKeyException if the habitat with given id
     *                                    doesn't exist in the hotel
     */
    public void changeHabitatArea(String id, int area)
            throws UnknownHabitatKeyException {
        Habitat habitat = getHabitat(id);
        habitat.setArea(area);
        changed();
    }

    /**
     * Changes the influence of a habitat on a species.
     *
     * @param habitatId the id of the habitat
     * @param speciesId the id of the species
     * @param influence the type of influence to be changed to
     * @throws UnknownHabitatKeyException if the habitat with given id
     *                                    doesn't exist in the hotel
     * @throws UnknownSpeciesKeyException if the species with given id
     *                                    doesn't exist in the hotel
     */
    public void changeHabitatInfluence(String habitatId, String speciesId, String influence)
            throws UnknownHabitatKeyException, UnknownSpeciesKeyException{
        Habitat habitat = getHabitat(habitatId);
        Species species = getSpecie(speciesId);
        habitat.changeInfluence(species, influence);
        changed();
    }

    public Tree plantTreeInHabitat(String habitatId, String treeId,
                                   String treeName, int treeAge,
                                   int cleaningDifficulty, String type)
            throws UnknownHabitatKeyException, DuplicateTreeKeyException,
            UnrecognizedEntryException, NonPositiveIntegerException{
        Habitat habitat = getHabitat(habitatId);
        Tree tree = registerTree(treeId, treeName, treeAge,
                cleaningDifficulty, type);
        habitat.addTree(tree);
        changed();
        return tree;
    }

    /**
     * Plants existing trees in the hotel into the given habitat.
     *
     * @param habitatId the id of the habitat to be planted in
     * @param treeIds   the ids of the trees that will be planted
     * @throws UnknownHabitatKeyException if the habitat with given id
     *                                    doesn't exist in the hotel
     * @throws UnknownTreeKeyException    if the tree with given id
     *                                    doesn't exist in the hotel
     */
    public void plantExistingTreesInHabitat(String habitatId, String... treeIds)
            throws UnknownHabitatKeyException, UnknownTreeKeyException {
        Habitat habitat = getHabitat(habitatId);

        for (String treeId : treeIds) {
            Tree tree = getTree(treeId);
            habitat.addTree(tree);
        }

        changed();
    }

    public boolean vaccinateAnimal(String vaccineId, String veterinarianId, String animalId)
            throws UnknownVaccineKeyException, UnknownEmployeeKeyException,
            UnknownAnimalKeyException, UnknownVeterinarianKeyException,
            VeterinarianNotAuthorizedException{
        Vaccination vaccination = registerVaccination(vaccineId, veterinarianId, animalId);
        vaccination.getDamageTerm();
        changed();
        return !vaccination.wasWrong();
    }

    /**
     * Read text input file and create domain entities.
     *
     * @param filename name of the text input file
     * @throws ImportFileException if there were any errors when importing the file
     */
    void importFile(String filename) throws ImportFileException {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(filename))) {
            String entry;
            while ((entry = reader.readLine()) != null) {
                setCurrentEntry(entry);
                String[] fields = entry.split("\\|");
                parseEntry(fields);
            }
        } catch (IOException | UnrecognizedEntryException e) {
            throw new ImportFileException(filename, e);
        }
    }

    /**
     * Parses an entry, using its fields, and imports
     * the entry type into the hotel.
     *
     * @param fields the fields of an entry
     * @throws UnrecognizedEntryException if the entry isn't recognizable
     *                                    by the program
     */
    private void parseEntry(String[] fields)
            throws UnrecognizedEntryException {
        switch (fields[0]) {
            case "ESPÉCIE" -> parseSpecie(fields);
            case "ANIMAL" -> parseAnimal(fields);
            case "TRATADOR" -> parseEmployee(fields, "TRT");
            case "VETERINÁRIO" -> parseEmployee(fields, "VET");
            case "HABITAT" -> parseHabitat(fields);
            case "ÁRVORE" -> parseTree(fields);
            case "VACINA" -> parseVaccine(fields);
            default -> throw new UnrecognizedEntryException(_currentEntry);
        }
    }

    /**
     * Parses a species entry, using its fields
     * and registers the species into the hotel.
     *
     * @param fields the fields of a species entry
     */
    private void parseSpecie(String[] fields)
            throws UnrecognizedEntryException{
        try {
            registerSpecies(fields[1], fields[2]);
        }
        catch (DuplicateSpeciesKeyException |
                DuplicateSpeciesNameException e) {
            throw new UnrecognizedEntryException(_currentEntry, e);
        }
    }

    /**
     * Parses an animal entry, using its fields
     * and registers the animal into the hotel.
     *
     * @param fields the fields of an animal entry
     * @throws UnrecognizedEntryException if any field of the entry isn't correct
     *                                    for the registration
     */
    private void parseAnimal(String[] fields)
            throws UnrecognizedEntryException {
        try {
            registerAnimal(fields[1], fields[2], fields[3], fields[4]);
        }
        catch (DuplicateAnimalKeyException |
               UnknownSpeciesKeyException | UnknownHabitatKeyException e) {
            throw new UnrecognizedEntryException(_currentEntry, e);
        }
    }

    /**
     * Parses an employee entry, using its fields
     * and registers the animal into the hotel.
     * If the entry has responsibility ids,
     * also add the responsibilities to the employee.
     *
     * @param fields the fields of a employee entry
     * @param type   the type of the employee
     * @throws UnrecognizedEntryException if any field of the entry isn't correct
     *                                    for the registration
     */
    private void parseEmployee(String[] fields, String type)
            throws UnrecognizedEntryException{
        try {
            registerEmployee(fields[1], fields[2], type);

            if (fields.length == 4) {
                String[] responsibilityIds = fields[3].split(",");
                addResponsibilities(fields[1], responsibilityIds);
            }
        }
        catch (DuplicateEmployeeKeyException | UnknownEmployeeKeyException |
                UnknownHabitatKeyException | UnknownSpeciesKeyException e) {
            throw new UnrecognizedEntryException(_currentEntry, e);
        }
    }

    /**
     * Parses a habitat entry, using its fields
     * and registers the habitat into the hotel.
     * If the entry has tree ids, also plants the trees in the habitat.
     *
     * @param fields the fields of a habitat entry
     * @throws UnrecognizedEntryException if any field of the entry isn't correct
     *                                    for the registration
     */
    private void parseHabitat(String[] fields)
            throws UnrecognizedEntryException {
        try {
            registerHabitat(fields[1], fields[2], Integer.parseInt(fields[3]));

            if (fields.length == 5) {
                String[] treeIds = fields[4].split(",");
                plantExistingTreesInHabitat(fields[1], treeIds);
            }
        }
        catch (DuplicateHabitatKeyException | NonPositiveIntegerException |
               UnknownHabitatKeyException | UnknownTreeKeyException e) {
            throw new UnrecognizedEntryException(_currentEntry, e);
        }
    }

    /**
     * Parses a tree entry, using its fields
     * and registers the tree into the hotel.
     *
     * @param fields the fields of a tree entry
     * @throws UnrecognizedEntryException if any field of the entry isn't correct
     *                                    for the registration
     */
    private void parseTree(String[] fields)
            throws UnrecognizedEntryException {
        try {
            registerTree(fields[1], fields[2],
                         Integer.parseInt(fields[3]),
                         Integer.parseInt(fields[4]),
                         fields[5]);
        }
        catch (DuplicateTreeKeyException | NonPositiveIntegerException e) {
            throw new UnrecognizedEntryException(_currentEntry, e);
        }
    }

    /**
     * Parses a vaccine entry, using its fields
     * and registers the vaccine into the hotel.
     *
     * @param fields the fields of a vaccine entry
     * @throws UnrecognizedEntryException if any field of the entry isn't correct
     *                                    for the registration
     */
    private void parseVaccine(String[] fields)
            throws UnrecognizedEntryException {
        try {
            if (fields.length == 4) {
                registerVaccine(fields[1], fields[2], fields[3]);
            }
            else {
                registerVaccine(fields[1], fields[2]);
            }
        }
        catch (DuplicateVaccineKeyException | UnknownSpeciesKeyException e) {
            throw new UnrecognizedEntryException(_currentEntry, e);
        }
    }

}
