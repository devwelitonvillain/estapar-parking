package com.estapar.adapter.in.web.webhook.dto;

import com.estapar.model.valueobject.EventType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class EntryEventDto extends WebhookEventDto {

    @NotNull(message = "Entry time is required")
    @JsonProperty("entry_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime entryTime;

    public EntryEventDto() {
        super();
    }

    public EntryEventDto(String licensePlate, LocalDateTime entryTime) {
        super(licensePlate, EventType.ENTRY);
        this.entryTime = entryTime;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

}
