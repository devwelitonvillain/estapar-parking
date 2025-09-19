package com.estapar.port.in.revenue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface RevenueQueryUseCase {

    RevenueResult execute(LocalDate date, String sector);

    record RevenueResult(
            BigDecimal amount,
            String currency,
            LocalDateTime timestamp
    ) {}

}
