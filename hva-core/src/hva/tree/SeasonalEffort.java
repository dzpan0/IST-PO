package hva.tree;

public enum SeasonalEffort {

    SPRING_EFFORT(1, 1),
    SUMMER_EFFORT(2, 1),
    FALL_EFFORT(5, 1),
    WINTER_EFFORT(0, 2);

    private final int _deciduousEffort;
    private final int _evergreenEffort;

    SeasonalEffort(int deciduousEffort, int evergreenEffort) {
        _deciduousEffort = deciduousEffort;
        _evergreenEffort = evergreenEffort;
    }

    public int getDeciduousEffort() {
        return _deciduousEffort;
    }

    public int getEvergreenEffort() {
        return _evergreenEffort;
    }

}
