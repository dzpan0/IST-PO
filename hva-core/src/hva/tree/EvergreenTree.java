package hva.tree;

import java.io.Serial;

import hva.season.Season;

public class EvergreenTree extends Tree {

    @Serial
    private static final long serialVersionUID = 202410102055L;

    public EvergreenTree(String id, String name, int cleaningDifficulty, int age, Season plantedSeason) {
        super(id, name, cleaningDifficulty, age, plantedSeason,
                new PlainEvergreenTreeCycle(plantedSeason));
    }

    @Override
    public String toString() {
        return super.toString() + "|" + "PERENE" + "|" +  getLeafStage();
    }

}
