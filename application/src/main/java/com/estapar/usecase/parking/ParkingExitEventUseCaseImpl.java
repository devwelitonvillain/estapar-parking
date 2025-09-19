package com.estapar.usecase.parking;

import com.estapar.model.entity.ParkingSession;
import com.estapar.model.entity.Revenue;
import com.estapar.model.entity.Spot;
import com.estapar.model.exception.DomainException;
import com.estapar.model.exception.SpotNotFoundException;
import com.estapar.model.valueobject.SessionStatus;
import com.estapar.port.in.parking.ParkingExitEventUseCase;
import com.estapar.port.out.GarageRepository;
import com.estapar.port.out.ParkingSessionRepository;
import com.estapar.port.out.RevenueRepository;
import com.estapar.port.out.SpotRepository;
import com.estapar.service.SessionCalculatorService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ParkingExitEventUseCaseImpl implements ParkingExitEventUseCase {

    private final ParkingSessionRepository parkingSessionRepository;
    private final GarageRepository garageRepository;
    private final SpotRepository spotRepository;
    private final RevenueRepository revenueRepository;

    public ParkingExitEventUseCaseImpl(
            ParkingSessionRepository parkingSessionRepository,
            GarageRepository garageRepository,
            SpotRepository spotRepository,
            RevenueRepository revenueRepository
    ) {
        this.parkingSessionRepository = parkingSessionRepository;
        this.garageRepository = garageRepository;
        this.spotRepository = spotRepository;
        this.revenueRepository = revenueRepository;
    }

    @Override
    public void processExit(final String licensePlate, final LocalDateTime exitTime) {
        var parkingSession = findParkingSessionByLicensePlate(licensePlate);
        var spot = findSpotById(parkingSession.getSpotId());
        var basePrice = findBasePriceBySector(spot.getSector());

        setSpotFree(spot);
        updateParkingSession(parkingSession, exitTime, basePrice);
        saveRevenue(parkingSession);
    }

    private ParkingSession findParkingSessionByLicensePlate(final String licensePlate) {
        return parkingSessionRepository.findByLicensePlateEntered(licensePlate)
                .orElseThrow(() -> new DomainException("Parking Session not found."));
    }

    private Spot findSpotById(final Long id) {
        return spotRepository.findById(id)
                .orElseThrow(() -> new SpotNotFoundException("Spot not found."));
    }

    private BigDecimal findBasePriceBySector(final String sector) {
        return garageRepository.findBasePriceBySector(sector);
    }

    private void setSpotFree(final Spot spot) {
        spot.setIsOccupied(false);
        spot.setUpdatedAt(LocalDateTime.now());
        spotRepository.save(spot);
    }

    private void updateParkingSession(final ParkingSession parkingSession, final LocalDateTime exitTime, final BigDecimal basePrice) {
        var finalAmount = SessionCalculatorService.calculateFinalAmount(
                basePrice,
                parkingSession.getEntryTime(),
                exitTime,
                parkingSession.getDynamicPriceRate()
        );

        parkingSession.setFinalAmount(finalAmount);
        parkingSession.setStatus(SessionStatus.COMPLETED);
        parkingSession.setExitTime(exitTime);
        parkingSession.setUpdatedAt(LocalDateTime.now());
        parkingSessionRepository.save(parkingSession);
    }

    private void saveRevenue(final ParkingSession parkingSession) {
        var revenue = new Revenue();
        revenue.setAmount(parkingSession.getFinalAmount());
        revenue.setCreatedAt(LocalDateTime.now());
        revenue.setCurrency("BRL");
        revenue.setSector(parkingSession.getSector());
        revenue.setParkingSessionId(parkingSession.getId());
        revenue.setTransactionDate(parkingSession.getExitTime().toLocalDate());
        revenue.setTransactionTimestamp(LocalDateTime.now());
        revenueRepository.save(revenue);
    }

}
