package com.estapar.adapter.in.web.webhook;

import com.estapar.adapter.in.web.webhook.dto.EntryEventDto;
import com.estapar.adapter.in.web.webhook.dto.ExitEventDto;
import com.estapar.adapter.in.web.webhook.dto.ParkedEventDto;
import com.estapar.adapter.in.web.webhook.dto.WebhookEventDto;
import com.estapar.port.in.parking.ParkingEntryEventUseCase;
import com.estapar.port.in.parking.ParkingExitEventUseCase;
import com.estapar.port.in.parking.ParkingParkedEventUseCase;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);

    private final ParkingEntryEventUseCase parkingEntryEventUseCase;
    private final ParkingParkedEventUseCase parkingParkedEventUseCase;
    private final ParkingExitEventUseCase parkingExitEventUseCase;

    public WebhookController(
            final ParkingEntryEventUseCase parkingEntryEventUseCase,
            final ParkingParkedEventUseCase parkingParkedEventUseCase,
            final ParkingExitEventUseCase parkingExitEventUseCase
    ) {
        this.parkingEntryEventUseCase = parkingEntryEventUseCase;
        this.parkingParkedEventUseCase = parkingParkedEventUseCase;
        this.parkingExitEventUseCase = parkingExitEventUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> handleWebhookEvent(@Valid @RequestBody WebhookEventDto event) {
        logger.info("Received webhook event: {} for vehicle: {}", event.getEventType(), event.getLicensePlate());

        switch (event.getEventType()) {
            case ENTRY -> handleEntryEvent((EntryEventDto) event);
            case PARKED -> handleParkedEvent((ParkedEventDto) event);
            case EXIT -> handleExitEvent((ExitEventDto) event);
            default -> {
                logger.warn("Unknown event type received: {}", event.getEventType());
                return ResponseEntity.badRequest().build();
            }
        }

        logger.info("Successfully processed {} event for vehicle: {}", event.getEventType(), event.getLicensePlate());
        return ResponseEntity.ok().build();
    }

    private void handleEntryEvent(EntryEventDto event) {
        logger.debug("Processing entry for vehicle {} at {}", event.getLicensePlate(), event.getEntryTime());
        parkingEntryEventUseCase.processEntry(event.getLicensePlate(), event.getEntryTime());
    }

    private void handleParkedEvent(ParkedEventDto event) {
        logger.debug("Processing parked event for vehicle {} at coordinates {}, {}", event.getLicensePlate(), event.getLat(), event.getLng());
        parkingParkedEventUseCase.processParked(event.getLicensePlate(), event.getLat(), event.getLng());
    }

    private void handleExitEvent(ExitEventDto event) {
        logger.debug("Processing exit for vehicle {} at {}", event.getLicensePlate(), event.getExitTime());
        parkingExitEventUseCase.processExit(event.getLicensePlate(), event.getExitTime());
    }

}
