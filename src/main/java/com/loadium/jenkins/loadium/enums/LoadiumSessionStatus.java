package com.loadium.jenkins.loadium.enums;

/**
 * Created by furkanbrgl on 17/11/2017.
 */
public enum LoadiumSessionStatus {

    FAILED(-1),
    INITIALIZING(1),
    STARTING(2),
    STARTED(3),
    FINISHED(4);

    private int level;

    LoadiumSessionStatus(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
