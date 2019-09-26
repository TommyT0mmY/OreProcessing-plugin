package com.mccitylife.oreprocessing.utility;

public enum Permissions {
    PLACE_SIGN("placesign"),
    GET_SHOVEL("processhovel"),
    MINE_COAL("mine.coal"),
    MINE_IRON("mine.iron"),
    MINE_GOLD("mine.gold"),
    MINE_DIAMOND("mine.diamond"),
    MINE_EMERALD("mine.emerald"),
    PUMP_OIL("pump.oil"),
    PROCESS_COAL("process.coal"),
    PROCESS_IRON("process.iron"),
    PROCESS_GOLD("process.gold"),
    PROCESS_DIAMOND("process.diamond"),
    PROCESS_EMERALD("process.emerald"),
    PROCESS_OIL("process.oil");

    private String node;

    Permissions(String node) {
        this.node = node;
    }

    public String getNode() { return "oreprocessing." + node; }
}
