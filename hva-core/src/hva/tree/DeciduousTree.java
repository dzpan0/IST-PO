package hva.tree;

import java.io.Serial;

import hva.season.Season;

public class DeciduousTree extends Tree {

    @Serial
    private static final long serialVersionUID = 202410102101L;

    public DeciduousTree(String id, String name, int cleaningDifficulty, int age, Season plantedSeason) {
        super(id, name, cleaningDifficulty, age, plantedSeason,
                new PlainDeciduousTreeCycle(plantedSeason));
    }

    @Override
    public String toString() {
        return super.toString() + "|" + "CADUCA" + "|" + getLeafStage();
    }

}
