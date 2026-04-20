package hva.tree;

import java.io.Serial;
import java.io.Serializable;

import hva.season.Season;

public abstract class TreeCycle implements Serializable {

    @Serial
    private static final long serialVersionUID = 202410231910L;

    private Season _currentSeason;
    protected SeasonalLeafStage _seasonalLeafStage;
    protected SeasonalEffort _seasonalEffort;

    protected TreeCycle(Season season) {
        _currentSeason = season;
        _seasonalLeafStage = SeasonalLeafStage.values()[season.getCode()];
        _seasonalEffort = SeasonalEffort.values()[season.getCode()];
    }

    protected Season getSeason() {
        return _currentSeason;
    }

    protected SeasonalLeafStage getSeasonalLeafStage() {
        return _seasonalLeafStage;
    }

    protected SeasonalEffort getSeasonalEffort() {
        return _seasonalEffort;
    }

    protected void setSeasonalLeafStage(SeasonalLeafStage leafStage) {
        _seasonalLeafStage = leafStage;
    }

    protected void setSeasonalEffort(SeasonalEffort seasonalEffort) {
        _seasonalEffort = seasonalEffort;
    }

    protected void advanceSeason() {
         _currentSeason = _currentSeason.getNextSeason();
         int code = _currentSeason.getCode();
         setSeasonalLeafStage(SeasonalLeafStage.values()[code]);
         setSeasonalEffort(SeasonalEffort.values()[code]);
    }

    protected boolean passedYear(Season plantedSeason) {
        return _currentSeason.calculateSeasonsApart(plantedSeason) == 0;
    }

    protected abstract String getLeafStage();

    protected abstract int getSeasonalEffortValue();

}
