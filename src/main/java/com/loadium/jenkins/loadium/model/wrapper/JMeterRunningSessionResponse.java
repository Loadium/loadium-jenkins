package com.loadium.jenkins.loadium.model.wrapper;

import com.loadium.jenkins.loadium.model.LoadiumSessionBasicDetailsDTO;

/**
 * Created by furkanbrgl on 20/11/2017.
 * used to get running session's status.
 */
public class JMeterRunningSessionResponse {


    private LoadiumSessionBasicDetailsDTO loadiumSessionBasicDetailsDTO;

    private String status;

    public JMeterRunningSessionResponse() {
    }

    public LoadiumSessionBasicDetailsDTO getLoadiumSessionBasicDetailsDTO() {
        return loadiumSessionBasicDetailsDTO;
    }

    public void setLoadiumSessionBasicDetailsDTO(LoadiumSessionBasicDetailsDTO loadiumSessionBasicDetailsDTO) {
        this.loadiumSessionBasicDetailsDTO = loadiumSessionBasicDetailsDTO;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
