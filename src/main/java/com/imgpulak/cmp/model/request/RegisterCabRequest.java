package com.imgpulak.cmp.model.request;

public class RegisterCabRequest {
    private String cabId;
    private String cabState;
    private String cityId;

    public String getCabId() {
        return cabId;
    }

    public void setCabId(String cabId) {
        this.cabId = cabId;
    }

    public String getCabState() {
        return cabState;
    }

    public void setCabState(String cabState) {
        this.cabState = cabState;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
