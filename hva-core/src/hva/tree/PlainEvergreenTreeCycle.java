package hva.tree;

import java.io.Serial;

import hva.season.Season;

public class PlainEvergreenTreeCycle extends TreeCycle {

    @Serial
    private static final long serialVersionUID = 202410240339L;

    protected PlainEvergreenTreeCycle(Season season) {
        super(season);
    }

    @Override
    protected String getLeafStage() {
        return getSeasonalLeafStage().getEvergreenLeafStage();
    }

    @Override
    protected int getSeasonalEffortValue() {
        return getSeasonalEffort().getEvergreenEffort();
    }

}
