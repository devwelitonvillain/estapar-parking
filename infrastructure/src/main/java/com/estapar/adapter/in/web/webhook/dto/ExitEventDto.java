package com.estapar.adapter.in.web.webhook.dto;

import com.estapar.model.valueobject.EventType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ExitEventDto extends WebhookEventDto {

    @NotNull(message = "Exit time is required")
    @JsonProperty("exit_time")
    private LocalDateTime exitTime;

    public ExitEventDto() {
        super();
    }

    public ExitEventDto(String licensePlate, LocalDateTime exitTime) {
        super(licensePlate, EventType.EXIT);
        this.exitTime = exitTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }
}
