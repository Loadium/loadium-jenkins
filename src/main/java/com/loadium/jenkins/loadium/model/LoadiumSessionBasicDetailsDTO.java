package com.loadium.jenkins.loadium.model;


import com.loadium.jenkins.loadium.enums.LoadiumSessionStatus;

/**
 * Created by furkanbrgl on 20/11/2017.
 */
public class LoadiumSessionBasicDetailsDTO {

    private String testKey;
    private String sessionKey;
    private LoadiumSessionStatus sessionStatus;

    public LoadiumSessionBasicDetailsDTO() {
    }

    public String getTestKey() {
        return testKey;
    }

    public void setTestKey(String testKey) {
        this.testKey = testKey;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public LoadiumSessionStatus getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(LoadiumSessionStatus sessionStatus) {
        this.sessionStatus = sessionStatus;
    }
}
