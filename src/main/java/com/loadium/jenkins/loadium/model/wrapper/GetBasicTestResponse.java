package com.loadium.jenkins.loadium.model.wrapper;

import com.loadium.jenkins.loadium.model.LoadiumTestBasicDetailsDTO;

import java.util.List;

public class GetBasicTestResponse {

    private String status;

    private List<LoadiumTestBasicDetailsDTO> testBasicDetailsDTOs;

    public GetBasicTestResponse(String status, List<LoadiumTestBasicDetailsDTO> testBasicDetailsDTOs) {
        this.status = status;
        this.testBasicDetailsDTOs = testBasicDetailsDTOs;
    }

    public GetBasicTestResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<LoadiumTestBasicDetailsDTO> getTestBasicDetailsDTOs() {
        return testBasicDetailsDTOs;
    }

    public void setTestBasicDetailsDTOs(List<LoadiumTestBasicDetailsDTO> testBasicDetailsDTOs) {
        this.testBasicDetailsDTOs = testBasicDetailsDTOs;
    }
}
