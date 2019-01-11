package com.loadium.jenkins.loadium.model.wrapper;

public class DefaultResponse {

    private String status;

    public String getStatus() {
        return status;
    }

    public DefaultResponse(String status) {
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DefaultResponse() {
    }
}
