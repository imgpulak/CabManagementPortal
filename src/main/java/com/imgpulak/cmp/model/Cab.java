package com.imgpulak.cmp.model;

import com.imgpulak.cmp.fsm.CabState;

public class Cab {
    private String cabId;
    private CabState cabState;
    private String cityId;

    public String getCabId() {
        return cabId;
    }

    public void setCabId(String cabId) {
        this.cabId = cabId;
    }

    public CabState getCabState() {
        return cabState;
    }

    public void setCabState(CabState cabState) {
        this.cabState = cabState;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
