package com.estapar.port.in.parking;

import java.math.BigDecimal;

public interface ParkingParkedEventUseCase {

    void processParked(String licensePlate, BigDecimal latitude, BigDecimal longitude);

}
