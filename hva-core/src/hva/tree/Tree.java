package hva.tree;

import java.io.Serial;
import java.io.Serializable;

import hva.calculator.Calculator;
import hva.calculator.CleaningEffortCalculator;
import hva.season.Season;

public abstract class Tree implements Serializable {

    @Serial
    private static final long serialVersionUID = 202410090939L;

    private final String _id;
    private String _name;
    private int _age;
    private int _cleaningDifficulty;
    private Season _plantedSeason;
    private TreeCycle _treeCycle;
    private Calculator _cleaningEffortCalculator;

    public Tree(String id, String name, int age, int cleaningDifficulty,
                Season plantedSeason, TreeCycle treeCycle) {
        _id = id;
        _name = name;
        _age = age;
        _cleaningDifficulty = cleaningDifficulty;
        _plantedSeason = plantedSeason;
        _treeCycle = treeCycle;
        _cleaningEffortCalculator = new CleaningEffortCalculator(this);
    }

    public String getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public int getAge() {
        return _age;
    }

    public int getCleaningDifficulty() {
        return _cleaningDifficulty;
    }

    public Season getPlantedSeason() {
        return _plantedSeason;
    }

    public void setTreeCycle(TreeCycle treeCycle) {
        _treeCycle = treeCycle;
    }

    public void setCleaningEffortCalculator(Calculator calculator) {
        _cleaningEffortCalculator = calculator;
    }

    public String getLeafStage() {
        return _treeCycle.getLeafStage();
    }

    public int getSeasonalEffortValue() {
        return _treeCycle.getSeasonalEffortValue();
    }

    private void updateAge() {
        _age++;
    }

    public double calculateCleaningEffort() {
        return _cleaningEffortCalculator.calculate();
    }

    public void advanceSeason() {
        _treeCycle.advanceSeason();

        if (_treeCycle.passedYear(_plantedSeason)) {
            updateAge();
        }
    }

    @Override
    public String toString() {
        return "ÁRVORE|" + _id + "|" + _name + "|" + _age + "|" +
                _cleaningDifficulty;
    }
}
