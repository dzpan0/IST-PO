package hva.calculator;

import java.io.Serial;
import java.io.Serializable;

import hva.tree.Tree;

public class CleaningEffortCalculator implements Calculator, Serializable {

    @Serial
    private static final long serialVersionUID = 202410240513L;

    private Tree _tree;

    public CleaningEffortCalculator(Tree tree) {
        _tree = tree;
    }

    @Override
    public double calculate() {
        return (_tree.getCleaningDifficulty() *
                _tree.getSeasonalEffortValue() *
                Math.log(_tree.getAge() + 1));
    }
}
