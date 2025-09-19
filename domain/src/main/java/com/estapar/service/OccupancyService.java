package com.estapar.service;

import com.estapar.model.exception.DomainException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class OccupancyService {

    public static BigDecimal calculateOccupancyPercentage(int occupiedSpots, int totalSpots) {
        if (totalSpots <= 0)
            throw new DomainException("Total spots must be greater than 0");

        if (occupiedSpots < 0 || occupiedSpots > totalSpots)
            throw new DomainException("Occupied spots must be between 0 and total spots");

        if (occupiedSpots == 0) return BigDecimal.ZERO;

        return BigDecimal.valueOf(occupiedSpots)
                .divide(BigDecimal.valueOf(totalSpots), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public static boolean isFullyOccupied(int occupiedSpots, int totalSpots) {
        return occupiedSpots >= totalSpots;
    }
}

