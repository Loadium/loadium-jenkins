package com.loadium.jenkins.loadium.model.wrapper;

import com.loadium.jenkins.loadium.model.LoadiumSessionBasicDetailsDTO;

/**
 * Created by furkanbrgl on 20/11/2017.
 * used to start session
 */
public class StartSessionResponse {

    private LoadiumSessionBasicDetailsDTO session;

    private String status;


    public StartSessionResponse() {
    }

    public LoadiumSessionBasicDetailsDTO getSession() {
        return session;
    }

    public void setSession(LoadiumSessionBasicDetailsDTO session) {
        this.session = session;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
