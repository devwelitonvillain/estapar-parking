package com.estapar.adapter.in.web.revenue.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record RevenueRequestDto(
        @NotNull(message = "Date is required")
        @PastOrPresent(message = "Date cannot be in the future")
        @JsonProperty("date")
        LocalDate date,

        @NotBlank(message = "Sector is required")
        @JsonProperty("sector")
        String sector
) {
}
