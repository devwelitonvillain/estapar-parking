package com.estapar.adapter.in.web.revenue.dto;

import com.estapar.port.in.revenue.RevenueQueryUseCase;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RevenueResponseDto(
        @JsonProperty("amount")
        BigDecimal amount,

        @JsonProperty("currency")
        String currency,

        @JsonProperty("timestamp")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        LocalDateTime timestamp
) {

        public RevenueResponseDto(RevenueQueryUseCase.RevenueResult result) {
            this(result.amount(), result.currency(), result.timestamp());
        }
}
