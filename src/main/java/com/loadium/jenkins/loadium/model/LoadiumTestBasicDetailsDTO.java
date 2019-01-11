package com.loadium.jenkins.loadium.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.Date;

/**
 * Created by furkanbrgl on 10/11/17.
 */
public class LoadiumTestBasicDetailsDTO {
    private String testKey;
    private String testName;
    private Date createdTime;
    private Boolean favorite;
    private String testType;
    private String owner;


    public LoadiumTestBasicDetailsDTO() {
    }

    public String getTestKey() {
        return testKey;
    }

    public void setTestKey(String testKey) {
        this.testKey = testKey;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    @SuppressFBWarnings({"URF_UNREAD_FIELD", "EI_EXPOSE_REP2"})
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
