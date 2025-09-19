package com.estapar.usecase.parking;

import com.estapar.model.entity.ParkingSession;
import com.estapar.model.entity.Vehicle;
import com.estapar.model.exception.ParkingLotFullException;
import com.estapar.model.exception.VehicleAlreadyParkedException;
import com.estapar.model.valueobject.SessionStatus;
import com.estapar.port.in.parking.ParkingEntryEventUseCase;
import com.estapar.port.out.GarageRepository;
import com.estapar.port.out.ParkingSessionRepository;
import com.estapar.port.out.VehicleRepository;
import com.estapar.service.OccupancyService;
import com.estapar.service.PricingService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ParkingEntryEventUseCaseImpl implements ParkingEntryEventUseCase {

    private final ParkingSessionRepository parkingSessionRepository;
    private final GarageRepository garageRepository;
    private final VehicleRepository vehicleRepository;

    public ParkingEntryEventUseCaseImpl(
            ParkingSessionRepository parkingSessionRepository,
            GarageRepository garageRepository,
            VehicleRepository vehicleRepository
    ) {
        this.parkingSessionRepository = parkingSessionRepository;
        this.garageRepository = garageRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void processEntry(final String licensePlate, final LocalDateTime entryTime) {
        validateVehicleNotAlreadyParked(licensePlate);

        var availableSpots = garageRepository.countTotalSpotsByOpenHour(entryTime.toLocalTime());
        var totalSessionWithParkedStatus = parkingSessionRepository.totalSessionWithParkedOrEnteredStatus();

        validateGarageIsFull(totalSessionWithParkedStatus, availableSpots);

        Vehicle vehicle = registerVehicle(licensePlate);
        var dynamicPriceRate = calculateDynamicPrice(availableSpots, totalSessionWithParkedStatus);
        createParkingSession(vehicle, entryTime, dynamicPriceRate);
    }

    private void validateVehicleNotAlreadyParked(final String licensePlate) {
        parkingSessionRepository.findActiveByLicensePlate(licensePlate)
                .ifPresent(session -> {
                    throw new VehicleAlreadyParkedException(
                            "Vehicle " + licensePlate + " already has an active parking session");
                });
    }

    private void validateGarageIsFull(final Integer totalSessionWithParkedStatus, final Integer availableSpots) {
        if (OccupancyService.isFullyOccupied(totalSessionWithParkedStatus, availableSpots))
            throw new ParkingLotFullException("There are no available spots for parking.");
    }

    private BigDecimal calculateDynamicPrice(final Integer totalSessionWithParkedStatus, final Integer availableSpots) {
        var occupancyPercentage = OccupancyService.calculateOccupancyPercentage(availableSpots, totalSessionWithParkedStatus);
        return PricingService.calculatePriceMultiplier(occupancyPercentage);
    }

    private void createParkingSession(final Vehicle vehicle, final LocalDateTime entryTime, final BigDecimal dynamicPriceRate) {
        var parkingSession = new ParkingSession();
        parkingSession.setCreatedAt(LocalDateTime.now());
        parkingSession.setEntryTime(entryTime);
        parkingSession.setStatus(SessionStatus.ENTERED);
        parkingSession.setVehicleId(vehicle.getId());
        parkingSession.setDynamicPriceRate(dynamicPriceRate);
        parkingSessionRepository.save(parkingSession);
    }

    private Vehicle registerVehicle(final String licensePlate) {
        return vehicleRepository.findByLicensePlate(licensePlate).orElseGet(() -> {
            var vehicle = new Vehicle();
            vehicle.setLicensePlate(licensePlate);
            vehicle.setCreatedAt(LocalDateTime.now());
            return vehicleRepository.save(vehicle);
        });
    }

}
