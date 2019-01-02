package com.citypaths.com.citypaths.model;

import java.util.Date;

public class ErrorMessage {
    private Date incidentTime;
    private int errorCode = 9000;
    private String errorMessage;
    private String errorDetails;

    public ErrorMessage(){
        super();
        incidentTime = new Date();
    }

    public ErrorMessage(String errorMessage , String errorDetails){
        super();
        incidentTime = new Date();
        this.errorMessage = errorMessage;
        this.errorDetails = errorDetails;
    }

    public ErrorMessage(int errorCode , String errorMessage , String errorDetails){
        super();
        incidentTime = new Date();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorDetails = errorDetails;
    }

    public Date getIncidentTime() {
        return incidentTime;
    }

    public void setIncidentTime(Date incidentTime) {
        this.incidentTime = incidentTime;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }
}
