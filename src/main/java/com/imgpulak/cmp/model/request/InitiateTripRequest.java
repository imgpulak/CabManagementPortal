package com.imgpulak.cmp.model.request;

public class InitiateTripRequest {
    private String customerId;
    private String sourceCityId;
    private String destinationCityId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSourceCityId() {
        return sourceCityId;
    }

    public void setSourceCityId(String sourceCityId) {
        this.sourceCityId = sourceCityId;
    }

    public String getDestinationCityId() {
        return destinationCityId;
    }

    public void setDestinationCityId(String destinationCityId) {
        this.destinationCityId = destinationCityId;
    }
}
