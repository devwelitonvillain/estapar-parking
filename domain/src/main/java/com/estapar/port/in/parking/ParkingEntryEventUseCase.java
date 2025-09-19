package com.estapar.port.in.parking;

import java.time.LocalDateTime;

public interface ParkingEntryEventUseCase {

    void processEntry(String licensePlate, LocalDateTime entryTime);

}
