package com.estapar.model.exception;

public class SimulatorConnectionException extends RuntimeException {
    public SimulatorConnectionException(String message, Throwable e) {
        super(message, e);
    }

    public SimulatorConnectionException(String message) {
        super(message);
    }
}
