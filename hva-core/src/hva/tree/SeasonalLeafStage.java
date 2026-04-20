package hva.tree;

public enum SeasonalLeafStage {

    SPRING_LEAF_STAGE("GERARFOLHAS", "GERARFOLHAS"),
    SUMMER_LEAF_STAGE("COMFOLHAS", "COMFOLHAS"),
    FALL_LEAF_STAGE("LARGARFOLHAS", "COMFOLHAS"),
    WINTER_LEAF_STAGE("SEMFOLHAS", "LARGARFOLHAS");

    private final String _deciduousLeafStage;
    private final String _evergreenLeafStage;

    SeasonalLeafStage(String deciduousLeafStage, String evergreenLeafStage) {
        _deciduousLeafStage = deciduousLeafStage;
        _evergreenLeafStage = evergreenLeafStage;
    }

    public String getDeciduousLeafStage() {
        return _deciduousLeafStage;
    }

    public String getEvergreenLeafStage() {
        return _evergreenLeafStage;
    }

}
