package com.mccitylife.oreprocessing.utility;

public class Pair {

    private Object aFirst;
    private Object aSecond;

    public Pair(Object first, Object second) {
        this.aFirst = first;
        this.aSecond = second;
    }

    public Object first () {
        return aFirst;
    }

    public Object second () {
        return aSecond;
    }
}
