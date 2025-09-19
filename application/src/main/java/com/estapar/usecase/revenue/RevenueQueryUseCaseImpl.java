package com.estapar.usecase.revenue;

import com.estapar.model.entity.ParkingSession;
import com.estapar.model.entity.Revenue;
import com.estapar.port.in.revenue.RevenueQueryUseCase;
import com.estapar.port.out.GarageRepository;
import com.estapar.port.out.ParkingSessionRepository;
import com.estapar.port.out.RevenueRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class RevenueQueryUseCaseImpl implements RevenueQueryUseCase {

    private final RevenueRepository revenueRepository;
    private final GarageRepository garageRepository;

    public RevenueQueryUseCaseImpl(
            RevenueRepository revenueRepository,
            GarageRepository garageRepository
    ) {
        this.revenueRepository = revenueRepository;
        this.garageRepository = garageRepository;
    }

    @Override
    public RevenueResult execute(final LocalDate date, final String sector) {
        validateSectorExists(sector);

        List<Revenue> revenues = revenueRepository.findByDateAndSector(date, sector);
        BigDecimal totalAmount = calculateTotalAmount(revenues);

        return new RevenueResult(
                totalAmount,
                "BRL",
                LocalDateTime.now()
        );
    }

    private void validateSectorExists(final String sector) {
        boolean exists = garageRepository.existsBySector(sector);
        if (!exists) {
            throw new IllegalArgumentException("Sector not found: " + sector);
        }
    }

    private BigDecimal calculateTotalAmount(List<Revenue> revenues) {
        if (revenues.isEmpty()) return BigDecimal.ZERO;

        return  revenues.stream()
                .map(Revenue::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
