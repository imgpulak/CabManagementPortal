package com.imgpulak.cmp.exception;

public class NoCabAvailableException extends Exception {

    @Override
    public String getMessage() {
        return "No cab is available for now.";
    }
}
