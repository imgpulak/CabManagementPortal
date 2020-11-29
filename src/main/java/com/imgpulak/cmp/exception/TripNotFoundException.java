package com.imgpulak.cmp.exception;

public class TripNotFoundException extends Exception {
    @Override
    public String getMessage() {
        return "Trip not found.";
    }
}
