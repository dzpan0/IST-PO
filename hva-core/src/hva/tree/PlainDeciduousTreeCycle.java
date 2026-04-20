package hva.tree;

import java.io.Serial;

import hva.season.Season;

public class PlainDeciduousTreeCycle extends TreeCycle {

    @Serial
    private static final long serialVersionUID = 202410240331L;

    protected PlainDeciduousTreeCycle(Season season) {
        super(season);
    }

    @Override
    protected String getLeafStage() {
        return getSeasonalLeafStage().getDeciduousLeafStage();
    }

    @Override
    protected int getSeasonalEffortValue() {
        return getSeasonalEffort().getDeciduousEffort();
    }

}
