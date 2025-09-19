package com.estapar.service;

import java.math.BigDecimal;

public final class PricingService {

    public static BigDecimal calculatePriceMultiplier(BigDecimal occupancyPercentage) {
        return switch (occupancyPercentage) {
            case BigDecimal v when v.compareTo(BigDecimal.valueOf(25)) < 0  -> BigDecimal.valueOf(0.90);
            case BigDecimal v when v.compareTo(BigDecimal.valueOf(50)) < 0 -> BigDecimal.ONE;
            case BigDecimal v when v.compareTo(BigDecimal.valueOf(75)) < 0 -> BigDecimal.valueOf(1.10);
            default -> BigDecimal.valueOf(1.25);
        };
    }

}

