package hva.season;

public enum Season {

    SPRING(0),
    SUMMER(1),
    FALL(2),
    WINTER(3);

    private final int _code;

    Season(int code) {
        _code = code;
    }

    public int getCode() {
        return _code;
    }

    public Season getNextSeason() {
        return values()[(_code + 1) % Season.values().length];
    }

    public int calculateSeasonsApart(Season season) {
        return Math.abs(season.getCode() - _code);
    }

}
