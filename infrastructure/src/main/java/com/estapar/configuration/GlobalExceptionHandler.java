package com.estapar.configuration;

import com.estapar.model.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(DomainException ex, HttpServletRequest request) {
        logger.warn("Domain exception: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                "DOMAIN_EXCEPTION",
                ex.getMessage(),
                request.getRequestURI(),
                HttpStatus.CONFLICT.value()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(VehicleAlreadyParkedException.class)
    public ResponseEntity<ErrorResponse> handleVehicleAlreadyParked(VehicleAlreadyParkedException ex, HttpServletRequest request) {
        logger.warn("Vehicle already parked: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                "VEHICLE_ALREADY_PARKED",
                ex.getMessage(),
                request.getRequestURI(),
                HttpStatus.CONFLICT.value()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(VehicleInvalidSessionException.class)
    public ResponseEntity<ErrorResponse> handleSessionNotFound(VehicleInvalidSessionException ex, HttpServletRequest request) {
        logger.warn("Vehicle invalid session: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                "INVALID_SESSION",
                ex.getMessage(),
                request.getRequestURI(),
                HttpStatus.CONFLICT.value()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(ParkingLotFullException.class)
    public ResponseEntity<ErrorResponse> parkingLotFullException(ParkingLotFullException ex, HttpServletRequest request) {
        logger.warn("Parking lot full: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                "PARKING_LOT_FULL",
                ex.getMessage(),
                request.getRequestURI(),
                HttpStatus.CONFLICT.value()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        logger.error("Unexpected error occurred: ", ex);

        ErrorResponse error = new ErrorResponse(
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred. Please try again later.",
                request.getRequestURI(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(GarageNotFoundException.class)
    public ResponseEntity<ErrorResponse> garageNotFoundException(GarageNotFoundException ex, HttpServletRequest request) {
        logger.error("Garage not found: ", ex);

        ErrorResponse error = new ErrorResponse(
                "GARAGE_NOT_FOUND",
                ex.getMessage(),
                request.getRequestURI(),
                HttpStatus.NOT_FOUND.value()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(SpotNotFoundException.class)
    public ResponseEntity<ErrorResponse> garageNotFoundException(SpotNotFoundException ex, HttpServletRequest request) {
        logger.error("Spot not found: ", ex);

        ErrorResponse error = new ErrorResponse(
                "SPOT_NOT_FOUND",
                ex.getMessage(),
                request.getRequestURI(),
                HttpStatus.NOT_FOUND.value()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }



    public static class ErrorResponse {

        private final String code;
        private final String message;
        private final String path;
        private final int status;

        private final LocalDateTime timestamp;

        public ErrorResponse(String code, String message, String path, int status) {
            this.code = code;
            this.message = message;
            this.path = path;
            this.status = status;
            this.timestamp = LocalDateTime.now();
        }

        public String getCode() { return code; }
        public String getMessage() { return message; }
        public String getPath() { return path; }
        public int getStatus() { return status; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }

}
