package com.estapar.service;

import com.estapar.model.exception.DomainException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

public final class SessionCalculatorService {

    private static final int FREE_MINUTES = 30;
    private static final int MINUTES_PER_HOUR = 60;

    public static BigDecimal calculateFinalAmount(BigDecimal basePrice, LocalDateTime entryTime, LocalDateTime exitTime, BigDecimal dynamicPriceRate) {
        if (basePrice == null || entryTime == null || exitTime == null || dynamicPriceRate == null)
            throw new DomainException("Entry base price, time, exit time and dynamic price rate cannot be null");

        if (exitTime.isBefore(entryTime))
            throw new DomainException("Exit time cannot be before entry time");

        long totalMinutes = calculateTotalMinutes(entryTime, exitTime);
        if (totalMinutes <= FREE_MINUTES) return BigDecimal.ZERO;

        int billableHours = calculateBillableHours(totalMinutes);

        return dynamicPriceRate
                .multiply(basePrice)
                .multiply(BigDecimal.valueOf(billableHours))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public static long calculateTotalMinutes(LocalDateTime entryTime, LocalDateTime exitTime) {
        Duration duration = Duration.between(entryTime, exitTime);
        return duration.toMinutes();
    }

    public static int calculateBillableHours(long billableMinutes) {
        if (billableMinutes <= 0) {
            return 0;
        }

        return (int) Math.ceil((double) billableMinutes / MINUTES_PER_HOUR);
    }

}
