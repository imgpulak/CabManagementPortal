package com.imgpulak.cmp.exception;

public class CityNotFoundException extends Exception {

    @Override
    public String getMessage() {
        return "City not found";
    }
}
