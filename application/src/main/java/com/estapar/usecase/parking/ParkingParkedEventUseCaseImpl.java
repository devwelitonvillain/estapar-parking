package com.estapar.usecase.parking;

import com.estapar.model.entity.ParkingSession;
import com.estapar.model.entity.Spot;
import com.estapar.model.exception.*;
import com.estapar.model.valueobject.SessionStatus;
import com.estapar.port.in.parking.ParkingParkedEventUseCase;
import com.estapar.port.out.GarageRepository;
import com.estapar.port.out.ParkingSessionRepository;
import com.estapar.port.out.SpotRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ParkingParkedEventUseCaseImpl implements ParkingParkedEventUseCase {

    private final ParkingSessionRepository parkingSessionRepository;
    private final GarageRepository garageRepository;
    private final SpotRepository spotRepository;

    public ParkingParkedEventUseCaseImpl(
            ParkingSessionRepository parkingSessionRepository,
            GarageRepository garageRepository,
            SpotRepository spotRepository
    ) {
        this.parkingSessionRepository = parkingSessionRepository;
        this.garageRepository = garageRepository;
        this.spotRepository = spotRepository;
    }

    @Override
    public void processParked(final String licensePlate, final BigDecimal latitude, final BigDecimal longitude) {
        var parkingSession = validateVehicleEnteredInGarage(licensePlate);
        var spot = findSpotByCoordinates(latitude, longitude);
        validateGarageIsOpened(spot.getSector());
        setSpotOccupied(spot);
        updateParkingSession(parkingSession, spot);
    }

    private ParkingSession validateVehicleEnteredInGarage(final String licensePlate) {
        var sessionOp = parkingSessionRepository.findActiveByLicensePlate(licensePlate);

        if (sessionOp.isPresent() && SessionStatus.PARKED.equals(sessionOp.get().getStatus()))
            throw new VehicleAlreadyParkedException("Vehicle " + licensePlate + " already has an active parking session");

        if (sessionOp.isEmpty())
            throw new VehicleInvalidSessionException("Vehicle " + licensePlate + " has not entered the garage");

        return sessionOp.get();
    }

    private void validateGarageIsOpened(final String sector) {
        LocalTime garageOpenHour = garageRepository.getOpenHourBySector(sector);
        if (LocalTime.now().isBefore(garageOpenHour))
            throw new DomainException("Garage is not open yet.");
    }

    private Spot findSpotByCoordinates(final BigDecimal latitude, final BigDecimal longitude) {
        return spotRepository.findByCoordinates(latitude, longitude)
                .orElseThrow(() -> new SpotNotFoundException("Spot not found for coordinates: " + latitude + ", " + longitude));
    }

    private void setSpotOccupied(final Spot spot) {
        spot.setIsOccupied(true);
        spotRepository.save(spot);
    }

    private void updateParkingSession(final ParkingSession parkingSession, final Spot spot) {
        parkingSession.setStatus(SessionStatus.PARKED);
        parkingSession.setSpotId(spot.getId());
        parkingSession.setSector(spot.getSector());
        parkingSession.setParkedTime(LocalDateTime.now());
        parkingSession.setUpdatedAt(LocalDateTime.now());
        parkingSessionRepository.save(parkingSession);
    }

}
