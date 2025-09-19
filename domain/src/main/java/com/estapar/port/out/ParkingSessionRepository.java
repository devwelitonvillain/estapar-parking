package com.estapar.port.out;

import com.estapar.model.entity.ParkingSession;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ParkingSessionRepository {

    Optional<ParkingSession> findActiveByLicensePlate(String licensePlate);
    Integer totalSessionWithParkedOrEnteredStatus();
    ParkingSession save(ParkingSession parkingSession);
    boolean existsEnteredSession(String licensePlate);
    Optional<ParkingSession> findByLicensePlateEntered(String licensePlate);
    List<ParkingSession> findByDateAndSector(LocalDate date, String sector);

}
