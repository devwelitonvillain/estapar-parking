package com.estapar.adapter.in.web.webhook.dto;

import com.estapar.model.valueobject.EventType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "event_type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = EntryEventDto.class, name = "ENTRY"),
        @JsonSubTypes.Type(value = ParkedEventDto.class, name = "PARKED"),
        @JsonSubTypes.Type(value = ExitEventDto.class, name = "EXIT")
})
public abstract class WebhookEventDto {

    @NotBlank(message = "License plate is required")
    @JsonProperty("license_plate")
    private String licensePlate;

    @NotNull(message = "Event type is required")
    @JsonProperty("event_type")
    private EventType eventType;

    protected WebhookEventDto() {}

    protected WebhookEventDto(String licensePlate, EventType eventType) {
        this.licensePlate = licensePlate;
        this.eventType = eventType;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}
