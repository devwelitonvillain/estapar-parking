package com.estapar.port.in.parking;

import java.time.LocalDateTime;

public interface ParkingExitEventUseCase {

    void processExit(String licensePlate, LocalDateTime exitTime);

}
